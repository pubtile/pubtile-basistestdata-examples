package com.pubtile.basistestdata.example.order.dto;

import com.pubtile.basistestdata.example.base.dto.create.BaseCreateBillRequestDTO;
import com.pubtile.basistestdata.example.order.po.BillOrderPO;
import com.pubtile.basistestdata.example.stock.dto.StockBookingRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

/**
 * 创建订单DTO
 *
 * @author jiayan
 * @version 0.0.1 2021-08-14
 * @since 0.0.1 2021-08-14
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BillOrderCreatingRequestDTO extends BaseCreateBillRequestDTO<BillOrderPO, BillOrderCreatingRequestDTO.ItemDTO> {

    public static Converter CONVERTER = Mappers.getMapper(Converter.class);

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单地址")
    private String orderAddress;

    @ApiModelProperty(value = "订单明细")
    private ArrayList<ItemDTO> details;

    @Override
    public BillOrderPO convertToBeCreatedPO() {
        return CONVERTER.toPO(this);
    }

    @Data
    @ApiModel(value="BillOrderDetail对象", description="")
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class ItemDTO extends BaseCreateBillRequestDTO.ItemDTO {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "订单编号")
        private String orderNumber;

        @ApiModelProperty(value = "订单Id")
        private Long orderId;

        @Override
        public Long getHeaderId() {
            return this.getOrderId();
        }
        @Override
        public void setHeaderId(Long id) {
            this.setOrderId(id);
        }
    }

    @Mapper
    public static abstract class Converter extends BaseCreateBillRequestDTO.Converter {
//        @CustomerCreateRequestMapping
        public abstract BillOrderPO toPO(BillOrderCreatingRequestDTO header);

        public abstract StockBookingRequestDTO toStockBookingRequestDTO(BillOrderCreatingRequestDTO billOrderDetailCreatingDto);

    }

//    @Retention(RetentionPolicy.CLASS)
//    @Mapping(target = "createdBy", expression = "java(com.pubtile.basistestdata.example.base.UserConstant.SYSTEM_USER_ID)")
//    @Mapping(target = "updatedBy", expression = "java(com.pubtile.basistestdata.example.base.UserConstant.SYSTEM_USER_ID)")
//    public @interface CustomerCreateRequestMapping { }
}
