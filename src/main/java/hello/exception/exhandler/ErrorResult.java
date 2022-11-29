package hello.exception.exhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

//이 class 에서 오류에 대한 내용을 담는다 ENUM 으로 생성해서 만들어도 된다.
//간략하게 code , message 담아 뒀다.
@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private String message;

}
