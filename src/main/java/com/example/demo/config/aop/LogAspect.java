package com.example.demo.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component //빈등록
@Aspect //포인트컷을 설정할수 있다.(역할을 수행할 클래스 어노테이션 선언)
public class LogAspect {
    //어노테이션이 선언된 함수위에 실행될 메소드

    //slf4j
    Logger logger  = LoggerFactory.getLogger(LogAspect.class);
    @Around("@annotation(com.example.demo.config.aop.LogExecutionTime)") //해당어노테이션 주변에다가 밑의코드를 적용한다.
    //around 어노테이션을 사용함으로써 joinPoint를 받을 수있다. joinpoint -> 애노테이션이 붙여져있는 메소드가 들어옴
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed(); //조인포인트를 실행하겠다.
        stopWatch.stop();
        logger.info("관리자 조회 서비스 실행시간 측정"+ stopWatch.prettyPrint());
        return proceed; //결과를 리턴하겠다.
    }

}
