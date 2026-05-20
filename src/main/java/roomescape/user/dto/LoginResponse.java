package roomescape.user.dto;

public class LoginResponse {

    private final Long id;
    private final String nickname;
    private final String token;

    public LoginResponse(UserResponse userResponse, String token) {
        this.id = userResponse.getId();
        this.nickname = userResponse.getNickname();
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getToken() {
        return token;
    }
}
