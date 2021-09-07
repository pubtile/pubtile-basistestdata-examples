package com.pubtile.basistestdata.example.base.dto.create;

import com.pubtile.basistestdata.example.base.UserConstant;
import com.pubtile.basistestdata.example.base.dto.BaseRequestDTO;
import com.pubtile.basistestdata.example.base.object.ItemDetailObject;
import com.pubtile.basistestdata.example.base.object.ICustomerRelatedObject;
import com.pubtile.basistestdata.example.base.po.BaseBillPO;
import com.pubtile.basistestdata.example.order.dto.BillOrderCreatingRequestDTO;
import com.pubtile.basistestdata.example.order.po.BillOrderPO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
public abstract class BaseCreateBillRequestDTO<T extends BaseBillPO, ITEM extends BaseCreateBillRequestDTO.ItemDTO>
        extends BaseRequestDTO implements ICustomerRelatedObject, ICreateRequestDTO<T> {

    private Long customerId;

    private Integer status = 0;

    private BigDecimal totalQty;

    private BigDecimal totalAmount;

    List<ITEM> details;


    @Data
   public static abstract class ItemDTO implements ItemDetailObject {

        @ApiModelProperty(value = "SKU id")
        private Long skuId;

        @ApiModelProperty(value = "数量")
        private BigDecimal qty;

    }

    public static abstract class Converter {
        @AfterMapping
        protected void calculateTotalQty(BillOrderCreatingRequestDTO dto, @MappingTarget BillOrderPO po) {
            po.setCreatedBy(UserConstant.SYSTEM_USER_ID);
            po.setUpdatedBy(UserConstant.SYSTEM_USER_ID);
            AtomicReference<BigDecimal> totalQty= new AtomicReference<>(BigDecimal.valueOf(0L));
            po.getDetails().stream().forEach(billOrderItemPO -> {
                billOrderItemPO.setCreatedBy(UserConstant.SYSTEM_USER_ID);
                billOrderItemPO.setUpdatedBy(UserConstant.SYSTEM_USER_ID);
                totalQty.set(totalQty.get().add(billOrderItemPO.getQty()));
            });


//
//            BigDecimal totalQty = po.getDetails().stream().map(BaseBillPO.Item::getQty).reduce(BigDecimal.valueOf(0),BigDecimal::add);
            po.setTotalQty(totalQty.get());

        }

    }

}
