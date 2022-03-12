package study.springmvc.basic.request;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import study.springmvc.basic.HelloData;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서(void) 응답에 값을 직접 넣으면, view 조회 X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("OK");
    }

    /**
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * @RequestParam 사용 - 파라미터 이름으로 바인딩
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
        @RequestParam("username") String memberName,
        @RequestParam("age") int memberAge
    ) {
        log.info("username={}, age={}", memberName, memberAge);
        return "OK";
    }

    /**
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * @RequestParam 사용 - HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xxx") 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    /**
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * @RequestParam 사용 - String, int 등의 단순 타입이면 @RequestParam도 생략 가능
     * 주의! @RequestParam을 생략하면 스프링 MVC는 내부에서 required=false 적용
     *
     * 너무 생략하는 것도 과할 수 있음. @RequestParam이 있으면 명확하게 요청 파라미터에서 데이터에서 읽는다는 것을 알 수 있음.
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    /**
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * @RequestParam.required - 파라미터 필수 여부. 기본값 true
     * /request-param -> username이 없으므로 400 예외 발생
     * 주의! /request-param?username= -> 빈문자로 통과
     * 주의! int에 null을 입력하는 것은 불가능(500 예외 발생)하므로 Integer 사용(또는 defaultValue 사용)
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
        @RequestParam(required = true) String username,
        @RequestParam(required = false) Integer age
    ) {
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    /**
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * @RequestParam.defaultValue - 파라미터가 없는 경우 기본 값을 적용. 기본 값이 있기 때문에 required는 의미가 없음.
     * 참고! /request-param?username= -> 빈 문자의 경우에도 적용
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
        @RequestParam(required = true, defaultValue = "guest") String username,
        @RequestParam(required = false, defaultValue = "-1") int age
    ) {
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    /**
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * @RequestParam Map - key=value
     * @RequestParam MultiValueMap - key=[value1, value2, ...]
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "OK";
    }

    /**
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * @ModelAttribute 사용 - 요청 파라미터를 받아서 필요한 객체를 만들고 그 객체에 값을 넣어주는 과정 자동화
     * 참고! model.addAttribute(helloData) 코드도 함께 자동 적용
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "OK";
    }

    /**
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * @ModelAttribute 생략 가능
     *   - String, int 같은 단순 타입은 @RequestParam
     *   - argument resolver로 지정해둔 타입 외는 @ModelAttribute
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "OK";
    }
}
