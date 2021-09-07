package com.pubtile.basistestdata.example.base.optimisticLockRetry;

/**
 * retry 次数超过上限异常
 *
 * @author jiayan
 * @version 0.0.1 2021-08-15
 * @since 0.0.1 2021-08-15
 */
public class RetryException extends RuntimeException {
    public RetryException(String message) {
        super(message);
    }
}
