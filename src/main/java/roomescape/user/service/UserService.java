package roomescape.user.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.exception.ConflictException;
import roomescape.exception.NotFoundException;
import roomescape.exception.UnauthorizedException;
import roomescape.user.dto.JoinUserRequest;
import roomescape.user.dto.LoginUserRequest;
import roomescape.user.dto.UserResponse;
import roomescape.user.model.Role;
import roomescape.user.model.User;
import roomescape.user.repository.UserRepository;
import roomescape.exception.ErrorCode;

@Service
public class UserService {

    private static final Role DEFAULT = Role.USER;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse create(JoinUserRequest request) {
        try {
            User user = new User(request.userName(), request.password(), request.nickName(), DEFAULT);
            Long id = userRepository.create(user);
            return UserResponse.from(new User(id, request.userName(), request.password(), request.nickName(), DEFAULT, null));
        } catch (DuplicateKeyException e) {
            throw new ConflictException(ErrorCode.DUPLICATE_USER_NAME);
        }
    }

    @Transactional
    public UserResponse login(LoginUserRequest request) {
        User user = this.findByUserName(request.userName());

        if (!user.getPassword().equals(request.password())) {
            throw new UnauthorizedException(ErrorCode.INVALID_CREDENTIALS);
        }
        return UserResponse.from(user);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
