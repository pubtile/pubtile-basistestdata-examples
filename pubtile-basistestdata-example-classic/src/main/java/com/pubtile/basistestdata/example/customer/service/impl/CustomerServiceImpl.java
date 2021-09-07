package com.pubtile.basistestdata.example.customer.service.impl;

import com.pubtile.basistestdata.example.customer.entity.Customer;
import com.pubtile.basistestdata.example.customer.mapper.CustomerMapper;
import com.pubtile.basistestdata.example.customer.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户信息表 服务实现类
 *
 * @author jiayan
 * @since 2021-08-14
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
