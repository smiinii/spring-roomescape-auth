package roomescape.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.exception.ErrorCode;
import roomescape.exception.ForbiddenException;
import roomescape.user.model.Role;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenExtractor tokenExtractor;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider, TokenExtractor tokenExtractor) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = tokenExtractor.extract(request);

        String role = jwtTokenProvider.getRole(token);

        if (!Role.ADMIN.name().equals(role) && !Role.MANAGER.name().equals(role)) {
            throw new ForbiddenException(ErrorCode.INSUFFICIENT_PERMISSIONS);
        }

        return true;
    }
}
