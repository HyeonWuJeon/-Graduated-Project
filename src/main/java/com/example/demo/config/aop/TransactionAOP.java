package com.example.demo.config.aop;


import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

@Configuration
public class TransactionAOP {

    @Autowired
    private PlatformTransactionManager transactionManager; // LINE :: 각 DB 설정에서 설정한 PlatformTransactionManager 객체

    private static final String EXPRESSION = "execution(* com.example.demo..service.*Service.*(..)))"; // LINE :: 포인트 컷

    @Bean
    public TransactionInterceptor transactionAdvice() {
        List<RollbackRuleAttribute> rollbackRules = Collections.singletonList(new RollbackRuleAttribute(Exception.class)); // LINE :: 트랜잭션 롤백 규칙 선언

        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setRollbackRules(rollbackRules);           // LINE :: 트랜잭션 롤백 규칙 설정
        transactionAttribute.setName("*");                              // LINE :: 트랜잭션 이름


        MatchAlwaysTransactionAttributeSource attributeSource = new MatchAlwaysTransactionAttributeSource();
        attributeSource.setTransactionAttribute(transactionAttribute);

        return new TransactionInterceptor((TransactionManager) transactionManager, attributeSource);
    }

    @Bean
    public Advisor transactionAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
    }

}
