package roomescape.exception;

public class UnauthorizedException extends CustomBusinessException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
