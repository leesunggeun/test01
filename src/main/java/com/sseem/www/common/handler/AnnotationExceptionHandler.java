package com.sseem.www.common.handler;

import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by jaejin on 16. 2. 3..
 */
@ControllerAdvice
@EnableWebMvc
public class AnnotationExceptionHandler {
    @ExceptionHandler(HttpSessionRequiredException.class)
    public ModelAndView httpSessionRequiredException(Exception e) {
        return new ModelAndView("redirect:/");
    }
}
