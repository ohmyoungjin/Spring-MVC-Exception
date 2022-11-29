package hello.exception.api;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api2")
public class ApiExceptionV2Controller {
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

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        //내가 정의한 Exception class
        if (id.equals("user-ex")) {
            log.info("user-ex !! {}", id);
            throw new UserException("사용자 오류");
        }
        return new MemberDto(id, "hello" + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
