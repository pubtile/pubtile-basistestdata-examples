package com.pubtile.basistestdata.example.order.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pubtile.basistestdata.example.base.po.BaseBillPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单明细
 * @author jiayan
 * @since 2021-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="BillOrderDetail对象", description="")
@TableName("bill_order_item")
public class BillOrderItemPO extends BaseBillPO.Item {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;



    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @Override
    public Long getHeaderId() {
        return orderId;
    }

    @Override
    public void setHeaderId(Long headerId) {
        this.orderId = headerId;
    }

    @Override
    public String getBillNumber() {
        return this.getOrderNumber();
    }

    @Override
    public void setBillNumber(String billNumber) {
        this.setOrderNumber(getBillNumber());
    }





}
