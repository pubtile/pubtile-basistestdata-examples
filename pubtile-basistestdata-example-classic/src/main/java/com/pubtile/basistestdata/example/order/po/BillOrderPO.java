package com.pubtile.basistestdata.example.order.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pubtile.basistestdata.example.base.po.BaseBillPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单
 * @author jiayan
 * @since 2021-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="BillOrder对象", description="")
@TableName("bill_order")
public class BillOrderPO extends BaseBillPO<BillOrderItemPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

//    @ApiModelProperty(value = "订单状态(0:未支付,10,已支付,20已发货,50已收货)")
//    private int orderStatus;

    @ApiModelProperty(value = "订单地址")
    private String orderAddress;


    @Override
    public String getBillNumber() {
        return this.getOrderNumber();
    }

    @Override
    public void setBillNumber(String billNumber) {
        this.setOrderNumber(billNumber);
    }
}
