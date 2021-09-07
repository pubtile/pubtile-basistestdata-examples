package com.pubtile.basistestdata.example.base.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础 PO
 *
 * @author jiayan
 * @version 0.6.17 2021-09-03
 * @since 0.6.17 2021-09-03
 */
@Data
public abstract class BasePO implements IBasePO {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * 表示该行数据是哪个系统用户创建的。这里特指系统用户，不包含顾客
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "最后更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updatedTime;

    @Version
    @ApiModelProperty(value = "版本号")
    private Long revision;

}

