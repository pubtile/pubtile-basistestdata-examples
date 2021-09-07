package com.pubtile.basistestdata.example.order.service;

import com.pubtile.basistestdata.example.base.ResultDto;
import com.pubtile.basistestdata.example.order.dto.BillOrderCreatingRequestDTO;
import com.pubtile.basistestdata.example.order.po.BillOrderPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单服务
 *
 * @author jiayan
 * @version 0.6.17 2021-09-03
 * @since 0.6.17 2021-09-03
 */
public interface IBillOrderService extends IService<BillOrderPO> {

    public ResultDto<String> createOrder(BillOrderCreatingRequestDTO billOrderCreatingDTO);


}
