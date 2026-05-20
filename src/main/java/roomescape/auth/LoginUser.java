package roomescape.auth;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 메서드의 파라미터에만 붙일 수 있도록 지정한다.
@Retention(RetentionPolicy.RUNTIME) // 프로그램이 실행되는 동안에도 유지되도록 한다.
public @interface LoginUser {
}
