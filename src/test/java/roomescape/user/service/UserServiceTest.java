package roomescape.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import roomescape.exception.ErrorCode;
import roomescape.exception.NotFoundException;
import roomescape.exception.UnauthorizedException;
import roomescape.user.dto.JoinUserRequest;
import roomescape.user.dto.LoginUserRequest;
import roomescape.user.dto.UserResponse;
import roomescape.user.model.Role;
import roomescape.user.model.User;
import roomescape.user.repository.UserRepository;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void 새로운_유저를_생성하면_UserResponse를_반환한다() {
        // given
        JoinUserRequest request = new JoinUserRequest("루크", "password123", "루크");
        Long expectedId = 1L;

        when(userRepository.create(any(User.class))).thenReturn(expectedId);

        // when
        UserResponse response = userService.create(request);

        // then
        assertThat(response.getId()).isEqualTo(expectedId);
        assertThat(response.getNickname()).isEqualTo("루크");
        verify(userRepository).create(any(User.class));
    }

    @Test
    void 존재하지_않는_이름으로_유저를_조회하면_예외가_발생한다() {
        // given
        String nonExistingName = "없는유저";
        when(userRepository.findByUserName(nonExistingName)).thenReturn(java.util.Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.findByUserName(nonExistingName))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
        verify(userRepository).findByUserName(nonExistingName);
    }

    @Test
    void 로그인에_성공하면_UserResponse를_반환한다() {
        // given
        LoginUserRequest request = new LoginUserRequest("루크", "password123");
        User expectedUser = new User(1L, "루크", "password123", "루크", Role.USER, null);

        when(userRepository.findByUserName("루크")).thenReturn(java.util.Optional.of(expectedUser));

        // when
        UserResponse response = userService.login(request);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNickname()).isEqualTo("루크");
    }

    @Test
    void 로그인_시_비밀번호가_다르면_예외가_발생한다() {
        // given
        LoginUserRequest request = new LoginUserRequest("루크", "wrongPass");
        User expectedUser = new User(1L, "루크", "password123", "루크", Role.USER, null);

        when(userRepository.findByUserName("루크")).thenReturn(java.util.Optional.of(expectedUser));

        // when & then
        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage(ErrorCode.INVALID_CREDENTIALS.getMessage());
    }
}
