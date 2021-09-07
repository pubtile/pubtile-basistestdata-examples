package com.pubtile.basistestdata.example.base.dto.update;

import com.pubtile.basistestdata.example.base.po.BasePO;

public interface IUpdateRequestDTO<T extends BasePO> {

    T convertToBeUpdatePO(T currentPO);


}
