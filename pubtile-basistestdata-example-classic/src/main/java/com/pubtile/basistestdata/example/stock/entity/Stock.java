package com.pubtile.basistestdata.example.stock.entity;

import com.pubtile.basistestdata.example.base.po.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * 库存entity
 *
 * @author jiayan
 * @version 0.6.17 2021-09-03
 * @since 0.6.17 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Stock对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class Stock extends BasePO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "SKU id")
    private Long skuId;

    @With
    @ApiModelProperty(value = "数量")
    private BigDecimal qty;

}
