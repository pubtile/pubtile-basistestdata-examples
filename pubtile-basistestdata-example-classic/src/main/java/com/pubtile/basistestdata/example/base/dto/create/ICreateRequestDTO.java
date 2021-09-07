package com.pubtile.basistestdata.example.base.dto.create;

import com.pubtile.basistestdata.example.base.po.BasePO;

public interface ICreateRequestDTO<T extends BasePO> {

    T convertToBeCreatedPO();
}
