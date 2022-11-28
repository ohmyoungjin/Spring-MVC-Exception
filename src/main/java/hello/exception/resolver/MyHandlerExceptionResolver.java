package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                //exception statusCode 를 400으로 변경해준다.
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                //새로운 ModelAndView를 넘겨준다.
                //빈 ModelAndView: new ModelAndView() 처럼 빈 ModelAndView 를 반환하면 뷰를 렌더링 하지
                //않고, 정상 흐름으로 서블릿이 리턴된다.
                //ModelAndView 지정: ModelAndView 에 View , Model 등의 정보를 지정해서 반환하면 뷰를 렌더링
                //한다.
                //null: null 을 반환하면, 다음 ExceptionResolver 를 찾아서 실행한다. 만약 처리할 수 있는
                //ExceptionResolver 가 없으면 예외 처리가 안되고, 기존에 발생한 예외를 서블릿 밖으로 던진다
                //이 부분에서 예외는 처리가 된다.
                return new ModelAndView();
//            } else if (ex instanceof RuntimeException) {
//                log.info("RuntimeException resolver to 500");
//                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                return new ModelAndView();
            }

        } catch (IOException e) {
            log.info("resolver ex", e);
        }

        return null;
    }
}
