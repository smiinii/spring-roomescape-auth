package roomescape.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginUserArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터에 @LoginUser 어노테이션이 붙어있는지 확인합니다.
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    // 2. 조건에 맞는다면, 실제로 어떤 값을 파라미터에 넣어줄지 결정합니다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        // 1. 요청(Request) 객체를 가져옵니다.
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 2. 쿠키에서 "token"을 꺼냅니다. (인터셉터에서 만들었던 로직과 비슷합니다!)
        String token = extractTokenFromCookie(request);

        // 3. 토큰을 해독해서 유저 아이디(username)를 반환합니다!
        return jwtTokenProvider.getUsername(token);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
