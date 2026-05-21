package roomescape.user.model;

public class User {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Role role;
    private Long storeId;

    public User(){}

    public User(String username, String password, String nickname, Role role) {
        this(null, username, password, nickname, role, null);
    }

    public User(Long id, String username, String password, String nickname, Role role, Long storeId) {
        validateUsername(username);
        validatePassword(password);
        validateNickname(nickname);
        validateRole(role);
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.storeId = storeId;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public Role getRole() {
        return role;
    }

    public Long getStoreId() {
        return storeId;
    }

    private void validateUsername(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("사용자 이름은 필수이며, 공백일 수 없습니다.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수이며, 공백일 수 없습니다.");
        }
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임은 필수이며, 공백일 수 없습니다.");
        }
    }

    private void validateRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("사용자 권한은 필수입니다.");
        }
    }
}
