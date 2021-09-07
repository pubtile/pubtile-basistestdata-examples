package com.pubtile.basistestdata.example.order.service.exception;

/**
 * 用户状态异常
 * @author jiayan
 * @version 0.6.17 2021/9/2
 * @since 0.6.17 2021/9/2
 */
public class IllegalCustomerStatusException extends RuntimeException{
    public IllegalCustomerStatusException() {
    }

    public IllegalCustomerStatusException(String message) {
        super(message);
    }
}
