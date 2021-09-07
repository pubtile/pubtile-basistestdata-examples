package com.pubtile.basistestdata.example.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pubtile.basistestdata.example.base.PoBuilder;
import com.pubtile.basistestdata.example.base.ResultDto;
import com.pubtile.basistestdata.example.base.dto.update.BaseUpdateRequestDTOMapper;
import com.pubtile.basistestdata.example.base.optimisticLockRetry.Retry;
import com.pubtile.basistestdata.example.base.optimisticLockRetry.RetryException;
import com.pubtile.basistestdata.example.stock.dto.StockBookingRequestDTO;
import com.pubtile.basistestdata.example.stock.entity.Stock;
import com.pubtile.basistestdata.example.stock.mapper.StockMapper;
import com.pubtile.basistestdata.example.stock.service.IStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 库存服务实现类
 *
 * @author jiayan
 * @version 0.6.17 2021-09-03
 * @since 0.6.17 2021-09-03
 */
@Service
@Slf4j
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {
    @Override
    @Retry
    public ResultDto<Void> bookStock(StockBookingRequestDTO stockBookingRequestDto) {
        ArrayList<StockBookingRequestDTO.Item> stockBookingRequestDetails = stockBookingRequestDto.getDetails() ;
        for (StockBookingRequestDTO.Item detail:stockBookingRequestDetails) {
            Stock currentStock = getBaseMapper().selectOne(new QueryWrapper<Stock>().lambda().eq(Stock::getSkuId,detail.getSkuId()));
            if (currentStock.getQty().compareTo(detail.getQty()) <0  ){
                return ResultDto.<Void>builder()
                        .success(false)
                        .errorMessage("stock is insufficient").build();
            }
            //TODO
//            Stock newStock = PoBuilder.refineUpdatePO(new Stock(),stockBookingRequestDto,currentStock);
//            Stock newStock = BaseUpdateRequestDTOMapper.INSTANCE.refineUpdatePO(new Stock(),stockBookingRequestDto,currentStock);
            Stock toBeUpdatedStock = stockBookingRequestDto.convertToBeUpdatePO(currentStock);
            toBeUpdatedStock.setQty(currentStock.getQty().subtract(detail.getQty()));
            int count = getBaseMapper().updateById(toBeUpdatedStock);
            if (count == 1) {
                continue;
            }else if (count == 0){
                throw new RetryException("stock optimistic Lock");
            }
        }
        return ResultDto.SUCCESS_WITHOUT_DATA;
    }
}
