package roomescape.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.exception.ErrorCode;
import roomescape.exception.UnauthorizedException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenExtractor tokenExtractor;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider, TokenExtractor tokenExtractor) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = tokenExtractor.extract(request);

        if (token == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        try {
            jwtTokenProvider.getUsername(token);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }

        return true;
    }
}
