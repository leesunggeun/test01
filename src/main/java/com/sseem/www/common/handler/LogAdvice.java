package com.sseem.www.common.handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by jin on 16. 4. 23..
 */
public class LogAdvice {

    public LogAdvice(){
//        System.out.println("***** LogAdvice *****");
    }

    public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("***** START *****");
        try{
            System.out.println("*");
            System.out.println("**");

            Object ret = joinPoint.proceed();

            System.out.println("***");
            System.out.println("****");

            return ret;
        }finally{
            System.out.println("***** END *****");
        }
    }

    public void before(){
//        System.out.println("***** before *****");
    }

    public Object around(ProceedingJoinPoint thisJoinPoint) throws Throwable{
        Object object = thisJoinPoint.proceed();
        System.out.println(object);
        System.out.println("***** around *****");
        return object;
    }

    public void after(){
//        System.out.println("***** after *****");
    }

    public void afterThrowing(JoinPoint joinPoint, Throwable exp){
//        System.out.println("***** after-throwing *****");
//        System.out.println("exp : " + exp.toString());
//        System.out.println("joinPoint kind : " + joinPoint.getKind());
    }

    public void afterReturning(Object obj){
//        if(obj!=null){
//            System.out.println("Object : " + obj.toString());
//        }
//        System.out.println("***** after-returning *****");
    }

}
