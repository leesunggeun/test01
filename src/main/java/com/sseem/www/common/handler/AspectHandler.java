package com.sseem.www.common.handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by jin on 16. 4. 25..
 */
@Aspect
public class AspectHandler {

    public AspectHandler(){
//        System.out.println("***** AspectHandler *****");
    }

    @Pointcut("execution(* com.sseem.www.service..*.*(..))")
    public void targetMethod(){
//        System.out.println("***** AspectHandler targetMethod *****");
    }

    @Before(value = "targetMethod()")
    public void before(JoinPoint thisJoinPoint){
//        System.out.println("kind : " + thisJoinPoint.getKind());
//        System.out.println("***** AspectHandler before *****");
    }

    @After(value = "targetMethod()")
    public void after(JoinPoint joinPoint){
//        System.out.println("kind : " + joinPoint.getKind());
//        System.out.println("***** AspectHandler after *****");
    }

    @AfterThrowing(pointcut = "targetMethod()", throwing = "exp")
    public void afterThrowing(JoinPoint joinPoint, Throwable exp){
        System.out.println("***** AspectHandler after-throwing *****");
        System.out.println("AspectHandler exp : " + exp.toString());
        System.out.println("AspectHandler joinPoint kind : " + joinPoint.getKind());
    }

    @AfterReturning(pointcut = "targetMethod()", returning = "obj")
    public void afterReturning(JoinPoint joinPoint, Object obj){
//        System.out.println("kind : " + joinPoint.getKind());
//        if(obj!=null){
//            System.out.println("obj : " + obj.toString());
//        }
//        System.out.println("***** AspectHandler after-returning *****");
    }

//    @Around(value = "targetMethod()")
    public void around(ProceedingJoinPoint thisJoinPoint){
//        System.out.println("kind : " + thisJoinPoint.getKind());
//        System.out.println("***** AspectHandler around *****");
    }

}
