package com.pubtile.basistestdata.example.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pubtile.basistestdata.example.base.PoBuilder;
import com.pubtile.basistestdata.example.base.ResultDto;
import com.pubtile.basistestdata.example.customer.entity.Customer;
import com.pubtile.basistestdata.example.customer.mapper.CustomerMapper;
import com.pubtile.basistestdata.example.order.dto.BillOrderCreatingRequestDTO;
import com.pubtile.basistestdata.example.order.mapper.BillOrderDetailMapper;
import com.pubtile.basistestdata.example.order.mapper.BillOrderMapper;
import com.pubtile.basistestdata.example.order.po.BillOrderItemPO;
import com.pubtile.basistestdata.example.order.po.BillOrderPO;
import com.pubtile.basistestdata.example.order.service.IBillOrderService;
import com.pubtile.basistestdata.example.order.service.exception.IllegalCustomerStatusException;
import com.pubtile.basistestdata.example.stock.dto.StockBookingRequestDTO;
import com.pubtile.basistestdata.example.stock.mapper.StockMapper;
import com.pubtile.basistestdata.example.stock.service.impl.StockServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

/**
 *  订单服务实现类
 *
 * @author jiayan
 * @since 2021-08-14
 */
@Service
public class BillOrderServiceImpl extends ServiceImpl<BillOrderMapper, BillOrderPO> implements IBillOrderService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final String ORDER_NUMBER_PREFIX = "SO";
    public static final int PAYING_ORDER_LIMIT = 4;


    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private BillOrderDetailMapper billOrderDetailMapper;


    /**
     * 创建订单
     * @param billOrderCreatingDTO
     * @return java.lang.String 订单编号
     * @author jiayan
     * @since 0.0.1 2021-08-14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDto<String> createOrder(BillOrderCreatingRequestDTO billOrderCreatingDTO) {
        //获得订单编号
        String orderNumber = generateOrderNumber(billOrderCreatingDTO);
        //
        Customer customer = customerMapper.selectById(billOrderCreatingDTO.getCustomerId());
        //blocked的用户无法下单
        if ("BLOCKED".equals(customer.getStatus())){
            throw new IllegalCustomerStatusException("customer is blocked,can't create order");
        }
        //校验有没有5笔未支付的订单
        int payingOrderCount = this.getBaseMapper().selectCount(new QueryWrapper<BillOrderPO>().lambda().eq(BillOrderPO::getCustomerId, billOrderCreatingDTO.getCustomerId()).eq(BillOrderPO::getStatus, 0));
        if (payingOrderCount > PAYING_ORDER_LIMIT){
            throw new IllegalStateException("paying order account is exceeded");
        }

        //扣减库存
        StockBookingRequestDTO stockBookingRequestDto = BillOrderCreatingRequestDTO.CONVERTER.toStockBookingRequestDTO(billOrderCreatingDTO);
        ResultDto<Void> bookingStockResult = stockService.bookStock(stockBookingRequestDto);
        if (!bookingStockResult.isSuccess()){
            return ResultDto.CONVERTER.result2Result(bookingStockResult);
//            return BillOrderServiceConverter.INSTANCE.result2Result(bookingStockResult);
        }

        //创建订单
        BillOrderPO orderPo = billOrderCreatingDTO.convertToBeCreatedPO();
        orderPo.setOrderNumber(orderNumber);

        getBaseMapper().insert(orderPo);
        PoBuilder.refineDetails(orderPo, (Consumer<BillOrderItemPO>) item -> item.setOrderNumber(orderPo.getOrderNumber()));
        orderPo.getDetails().stream().forEach(billOrderItemPo -> {
            billOrderDetailMapper.insert(billOrderItemPo);
        });
        return ResultDto.<String>builder().data(orderPo.getOrderNumber()).build();
    }


    private String generateOrderNumber(BillOrderCreatingRequestDTO billOrderCreatingDTO){
        String dateString = FORMATTER.format(LocalDateTime.now());
        RandomStringUtils.randomNumeric(6);
        return ORDER_NUMBER_PREFIX +billOrderCreatingDTO.getCustomerId()%16+dateString+ RandomStringUtils.randomNumeric(6);
    }

}
