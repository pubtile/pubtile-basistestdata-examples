package com.pubtile.basistestdata.example.base;


import com.pubtile.basistestdata.example.base.object.HasItemDetailsPersistanceObject;
import com.pubtile.basistestdata.example.base.object.ICustomerRelatedObject;
import com.pubtile.basistestdata.example.base.object.ItemDetailObject;
import com.pubtile.basistestdata.example.base.object.HasItemDetailsObject;
import com.pubtile.basistestdata.example.base.po.BasePO;

import java.util.List;
import java.util.function.Consumer;

/**
 * 构造和优化 Po
 *
 * @author jiayan
 * @version 0.6.17 2021-09-03
 * @since 0.6.17 2021-09-03
 */
public class PoBuilder {


    public static <T extends HasItemDetailsPersistanceObject> void refineDetails(T poWithDetails){
        List<ItemDetailObject> details= poWithDetails.getDetails();
        details.stream().forEach(item->{
            item.setHeaderId(poWithDetails.getId());
        });
    }

    public static <T extends HasItemDetailsPersistanceObject,D extends ItemDetailObject> void refineDetails(T poWithDetails, Consumer<D> additionalConsumer){
        List<D> details= poWithDetails.getDetails();
        details.stream().forEach(item->{
            item.setHeaderId(poWithDetails.getId());
            additionalConsumer.accept(item);
        });
    }


}
