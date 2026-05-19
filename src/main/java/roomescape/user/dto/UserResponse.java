package roomescape.user.dto;

import roomescape.user.model.User;

public class UserResponse {

    private final Long id;
    private final String nickname;
    private final String role;

    private UserResponse(Long id, String nickname, String role) {
        this.id = id;
        this.nickname = nickname;
        this.role = role;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getRole().name());
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getRole() {
        return role;
    }
}
