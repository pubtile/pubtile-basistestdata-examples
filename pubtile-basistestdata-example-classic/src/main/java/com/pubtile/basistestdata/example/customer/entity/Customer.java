package com.pubtile.basistestdata.example.customer.entity;

import com.pubtile.basistestdata.example.base.po.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author jiayan
 * @since 2021-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Customer对象", description="用户信息表")
public class Customer extends BasePO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别(M:male 男,F:female 女)")
    private String gender;

    @ApiModelProperty(value = "手机电话号码")
    private String mobile;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;


}
