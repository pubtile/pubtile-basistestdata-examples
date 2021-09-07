package com.pubtile.basistestdata.example.base.po;

import com.pubtile.basistestdata.example.base.object.HasItemDetailsPersistanceObject;
import com.pubtile.basistestdata.example.base.object.ItemDetailObject;

public interface IBaseBillObject<T extends ItemDetailObject> extends HasItemDetailsPersistanceObject<T> {

    Long getBillId();

    String getBillNumber();

    Long getCustomerId();

    Integer getStatus();

    void setBillId(Long billId);

    void setBillNumber(String billNumber);

    void setCustomerId(Long customerId);

    void setStatus(Integer status);

}
