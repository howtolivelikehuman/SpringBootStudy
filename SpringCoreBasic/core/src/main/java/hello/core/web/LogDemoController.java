package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    //컨테이너가 뜰때 -> MyLogger 의존관계 주입을 받아야 하는데 -> 얘는 Request가 와야지만 생김
    private final MyLogger mylogger;
    //Provider 방법
    //private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURI().toString();
        //MyLogger mylogger = myLoggerProvider.getObject();
        mylogger.setRequestURL(requestURL);
        System.out.println("myLogger = " + mylogger.getClass());
        Thread.sleep(1000);
        mylogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
