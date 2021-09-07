package com.pubtile.basistestdata.example.base.object;

import java.util.List;

/**
 * 包含明细的接口，可以是单据，基础数据，甚至配置。
 *
 * @author jiayan
 * @version 0.6.17 2021-09-06
 * @since 0.6.17 2021-09-06
 */
public interface HasItemDetailsPersistanceObject<T> extends HasItemDetailsObject<T>,IWithLongIdentifyObject {

}
