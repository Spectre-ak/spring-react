package com.spring.articles;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorHandler implements ErrorController{
    private static final String ERROR_PATH = "/page404";

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
    	System.out.println(request);
    	System.out.println("sa");
        Object status = 
            request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "page404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "page404";
            }
        }
        return "page404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH ;
    }

}
