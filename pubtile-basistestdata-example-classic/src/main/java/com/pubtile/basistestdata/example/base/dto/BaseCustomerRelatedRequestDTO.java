package com.pubtile.basistestdata.example.base.dto;

import lombok.Data;

@Data
public abstract  class BaseCustomerRelatedRequestDTO extends BaseRequestDTO {
    Long customerId;
}
