package com.pubtile.basistestdata.example.base.po;

import com.pubtile.basistestdata.example.base.object.ItemDetailObject;

public interface IBaseBillItemObject extends ItemDetailObject {

    default Long getBillId(){
        return this.getHeaderId();
    };

    String getBillNumber();

    default void setBillId(Long billId){
        this.setHeaderId(billId);
    };

    void setBillNumber(String billNumber);

}
