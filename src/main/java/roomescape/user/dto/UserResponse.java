package roomescape.user.dto;

import roomescape.user.model.User;

public class UserResponse {

    private final Long id;
    private final String nickname;

    private UserResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername());
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
