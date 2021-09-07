package com.pubtile.basistestdata.example.base.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.pubtile.basistestdata.example.base.object.ItemDetailObject;
import com.pubtile.basistestdata.example.base.object.ICustomerRelatedObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public abstract class BaseBillPO<T extends ItemDetailObject>  extends BasePO implements IBaseBillObject<T>, ICustomerRelatedObject {

    private Long customerId;

    //TODO 因为默认status在各个单据类型都不同，并且create 请求 status不能改变，所以在子类定义
    private Integer status;

    private BigDecimal totalQty;

    @Singular
    @TableField(exist = false)
    List<T> details;

    @Override
    public Long getBillId() {
        return this.getId();
    }

    @Override
    public void setBillId(Long billId) {
        this.setId(billId);
    }

    @Data
    public abstract static class Item extends BasePO implements IBaseBillItemObject{

        private Long skuId;

        private BigDecimal qty;
    }

}
