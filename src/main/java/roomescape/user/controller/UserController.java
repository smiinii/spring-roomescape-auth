package roomescape.user.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.auth.JwtTokenProvider;
import roomescape.auth.LoginUser;
import roomescape.user.dto.JoinUserRequest;
import roomescape.user.dto.LoginResponse;
import roomescape.user.dto.LoginUserRequest;
import roomescape.user.dto.UserResponse;
import roomescape.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<UserResponse> signUp(@RequestBody @Valid JoinUserRequest request) {
        UserResponse response = userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginUserRequest request) {
        UserResponse response = userService.login(request);

        String token = jwtTokenProvider.createToken(request.userName(), response.getRole(), response.getTokenVersion());

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(response, token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@LoginUser String username) {
        userService.logout(username);

        ResponseCookie cookie = ResponseCookie.from("token", "")
                .maxAge(0)
                .path("/")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
