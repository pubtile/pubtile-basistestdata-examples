package com.pubtile.basistestdata.example.stock.dto;

import com.pubtile.basistestdata.example.base.dto.BaseCustomerRelatedRequestDTO;
import com.pubtile.basistestdata.example.base.dto.update.BaseUpdateRequestDTOMapper;
import com.pubtile.basistestdata.example.base.dto.update.IUpdateRequestDTO;
import com.pubtile.basistestdata.example.base.object.HasItemDetailsObject;
import com.pubtile.basistestdata.example.base.object.ICustomerRelatedObject;
import com.pubtile.basistestdata.example.stock.entity.Stock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 库存占用DTO
 *
 * @author jiayan
 * @version 0.0.1 2021-08-14
 * @since 0.0.1 2021-08-14
 */
@Data
public class StockBookingRequestDTO extends BaseCustomerRelatedRequestDTO
        implements IUpdateRequestDTO<Stock>, HasItemDetailsObject<StockBookingRequestDTO.Item>, ICustomerRelatedObject {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "明细")
    private ArrayList<Item> details;

     @Override
    public Stock convertToBeUpdatePO(Stock currentStock) {
        return BaseUpdateRequestDTOMapper.INSTANCE.convertToBeUpdatedPO(new Stock(),this,currentStock);
    }

    @Data
    @ApiModel(value="BillOrderDetail对象", description="")
    public static class Item  {
        private static final long serialVersionUID = 1L;
        @ApiModelProperty(value = "SKU id")
        private Long skuId;
        @ApiModelProperty(value = "数量")
        private BigDecimal qty;
    }


}
