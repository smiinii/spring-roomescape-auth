package roomescape.theme.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import roomescape.auth.LoginUser;
import roomescape.theme.dto.ThemeRequest;
import roomescape.theme.dto.ThemesResponse;
import roomescape.theme.service.ThemeService;
import roomescape.user.model.User;
import roomescape.user.service.UserService;

@Validated
@RestController
@RequestMapping("/admin/themes")
public class AdminThemeController {

    private final ThemeService themeService;
    private final UserService userService;

    public AdminThemeController(ThemeService themeService, UserService userService) {
        this.themeService = themeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ThemesResponse> findAll(@LoginUser String userName) {
        User user = userService.findByUserName(userName);
        ThemesResponse responses = themeService.findAllByUser(user);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ThemeRequest request, @LoginUser String userName) {
        User user = userService.findByUserName(userName);
        themeService.create(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @NotNull(message = "테마 ID는 필수입니다.") @Positive(message = "테마 ID는 양수여야 합니다.") Long id,
            @LoginUser String userName) {
        User user = userService.findByUserName(userName);
        themeService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
