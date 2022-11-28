package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectMapper objectMapper2 = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {

            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if ("application/json".equals(acceptHeader)) {
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());
                    //Map 객체를 Json 형태로 바꿔준다 (ObjectMapper)
                    String result = objectMapper.writeValueAsString(errorResult);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    //JSON 형태로 넘겨줄 경우 파라미터를 적어주지 않는다.
                    //예외는 먹어버린 후 정상흐름으로 return 한다.
                    return new ModelAndView();
                } else {
                    // TEXT/HTML
                    //html로 렌더링해서 넘겨줄 경우 view path를 설정해준다
                    //resources/templates/error/500.html
                    return new ModelAndView("error/500");
                }//
            }

        } catch (IOException e) {
            log.info("resolver ex", e);
        }

        return null;
    }
}
