package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//error code 설정 및 error message 설정
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "error.bad")
public class BadRequestException extends RuntimeException{

}
