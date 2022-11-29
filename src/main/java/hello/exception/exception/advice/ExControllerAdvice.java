package hello.exception.exception.advice;

import hello.exception.api.ApiExceptionV2Controller;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//대상을 적용하지 않으면 모든 controller 적용 한다
//package , annotation, class 별로 적용 가능하다
@RestControllerAdvice(assignableTypes = ApiExceptionV2Controller.class)
public class ExControllerAdvice {
    //****해당 컨트롤러만 처리해준다 !!!
    //@RestController 이외에 @Controller로 사용해서
    //View로 렌더링도 가능하다.
    //controller error 발생 시 ExceptionResolver를 찾는다
    //@ExceptionHandler annotation 을 가장 먼저 찾아서
    //해당 하는 메소드의 특성을 살려줘서 return 해준다.
    //@ResponseStatus 붙여주지 않으면 *정상 처리가 되어 200으로 status code 200으로 반환해준다*.
    //이를 방지하기 위해 @ResponseStatus(HttpStatus.BAD_REQUEST) 붙여준다.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }
    //@ExceptionHandler (UserException.class) 적어줘야 하지만
    //파라미터로 해당하는 class를 받게 되면 생략 가능하다.
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        //ResponseEntity 감싸서 반환한다
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }
    //파라미터로 받은 Exception 이외에도 상속 받은 자식 Exception 까지 다 처리 해준다.
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

}
