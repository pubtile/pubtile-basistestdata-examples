package com.pubtile.basistestdata.example.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseRequestDTO {
    @ApiModelProperty(value = "租户id")
    private Long tenantId;
}
