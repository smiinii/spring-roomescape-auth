package roomescape.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.exception.ErrorCode;
import roomescape.exception.UnauthorizedException;
import roomescape.user.model.User;
import roomescape.user.service.UserService;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenExtractor tokenExtractor;
    private final UserService userService;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider, TokenExtractor tokenExtractor, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenExtractor = tokenExtractor;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = tokenExtractor.extract(request);

        if (token == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        String username;
        Integer tokenVersion;
        try {
            username = jwtTokenProvider.getUsername(token);
            tokenVersion = jwtTokenProvider.getVersion(token);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }

        if (tokenVersion == null) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }

        User user = userService.findByUserName(username);
        if (user.getTokenVersion() != tokenVersion) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }

        return true;
    }
}
