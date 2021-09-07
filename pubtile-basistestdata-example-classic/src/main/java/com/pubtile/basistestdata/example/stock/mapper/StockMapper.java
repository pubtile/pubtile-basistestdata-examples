package com.pubtile.basistestdata.example.stock.mapper;

import com.pubtile.basistestdata.example.stock.dto.StockBookingRequestDTO;
import com.pubtile.basistestdata.example.stock.entity.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 库存mapper
 *
 * @author jiayan
 * @version 0.6.17 2021-09-03
 * @since 0.6.17 2021-09-03
 */
public interface StockMapper extends BaseMapper<Stock> {

    int stockDecrease(@Param("header") StockBookingRequestDTO header, @Param("detail") StockBookingRequestDTO detail);

    int stockReduce(@Param("stock") Stock stock);

}
