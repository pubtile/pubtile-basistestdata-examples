package com.pubtile.basistestdata.example.stock.service;

import com.pubtile.basistestdata.example.base.ResultDto;
import com.pubtile.basistestdata.example.stock.dto.StockBookingRequestDTO;
import com.pubtile.basistestdata.example.stock.entity.Stock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 库存服务接口
 *
 * @author jiayan
 * @version 0.6.17 2021-09-03
 * @since 0.6.17 2021-09-03
 */
public interface IStockService extends IService<Stock> {


    ResultDto<Void> bookStock(StockBookingRequestDTO stockBookingRequestDto);
}
