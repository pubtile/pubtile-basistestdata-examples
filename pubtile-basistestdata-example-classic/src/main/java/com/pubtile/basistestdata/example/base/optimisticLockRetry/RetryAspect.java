package com.pubtile.basistestdata.example.base.optimisticLockRetry;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;


/**
 * retry aspect
 *
 * @author jiayan
 * @version 0.0.1 2021-08-15
 * @since 0.0.1 2021-08-15
 */
@Slf4j
@Aspect
@Component
public class RetryAspect {
    @Pointcut("@annotation(Retry)")
    public void retryPointcut() {

    }

    @Around("retryPointcut() && @annotation(retry)")
    public Object tryAgain(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        Assert.isTrue(retry.times() > 0, "@Retry times should be greater than 0!");

        int count = 0;
        do {
            count++;
            try {
                return joinPoint.proceed();
            } catch (RetryException e) {
                if (count > retry.times()) {
                    log.error("retry exceed max failed time {}!",retry.times());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new RetryException("retry exceed max failed time");
                }
            }
        } while (true);
    }
}