package com.pubtile.basistestdata.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pubtile.basistestdata.annotation.DataPrepare;
import com.pubtile.basistestdata.example.BaseTest;
import com.pubtile.basistestdata.example.ExampleApplication;
import com.pubtile.basistestdata.example.base.ResultDto;
import com.pubtile.basistestdata.example.order.dto.BillOrderCreatingRequestDTO;
import com.pubtile.basistestdata.example.order.mapper.BillOrderDetailMapper;
import com.pubtile.basistestdata.example.order.mapper.BillOrderMapper;
import com.pubtile.basistestdata.example.order.service.IBillOrderService;
import com.pubtile.basistestdata.example.order.service.exception.IllegalCustomerStatusException;
import com.pubtile.basistestdata.example.stock.entity.Stock;
import com.pubtile.basistestdata.example.stock.mapper.StockMapper;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@RecordApplicationEvents
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ExampleApplication.class)
@Slf4j
class IBillOrderServiceTest extends BaseTest {

    @Autowired
    ApplicationEvents events;

    @Autowired
    private IBillOrderService orderService;

    @Autowired
    private BillOrderMapper billOrderMapper;

    @Autowired
    private BillOrderDetailMapper billOrderDetailMapper;

    @Autowired
    private StockMapper stockMapper;



    /**
     * 场景：正常创建成功订单
     * 修改数据: 无需修改
     *
     * @author jiayan
     * @version 0.0.1 8/16/21
     * @since 0.0.1 8/16/21
     */
    @DataPrepare
    @Test
    void createOrder_happyCase(){
        long tenantId=1L,customerId = 2L,skuId1=3L,skuId2=4L;
        BigDecimal sku1Qty=BigDecimal.valueOf(1L);
        BigDecimal sku2Qty=BigDecimal.valueOf(2L);

        Stock sku1CurrentQty = stockMapper.selectOne(new QueryWrapper<Stock>().lambda().eq(Stock::getSkuId, skuId1));
        assertEquals(sku1CurrentQty.getQty(),BigDecimal.valueOf(3));
        Stock sku2CurrentQty = stockMapper.selectOne(new QueryWrapper<Stock>().lambda().eq(Stock::getSkuId, skuId2));
        assertEquals(sku2CurrentQty.getQty(),BigDecimal.valueOf(4L));

        createOrder(tenantId, customerId,Arrays.asList(new Pair<>(skuId1,sku1Qty),new Pair<>(skuId2,sku2Qty)));

        Stock currentQty = stockMapper.selectOne(new QueryWrapper<Stock>().lambda().eq(Stock::getSkuId, skuId1));
        //库存扣件正确
        log.info("stock-sku1 {}-{}={}",sku1CurrentQty.getQty(),sku1Qty,currentQty.getQty());
        assertEquals(sku1CurrentQty.getQty().subtract(sku1Qty),currentQty.getQty());

        currentQty = stockMapper.selectOne(new QueryWrapper<Stock>().lambda().eq(Stock::getSkuId, skuId2));
        log.info("stock-sku2 {}-{}={}",sku2CurrentQty.getQty(),sku2Qty,currentQty.getQty());
        assertEquals(sku2CurrentQty.getQty().subtract(sku2Qty),currentQty.getQty());
    }


    /**
     * 场景：库存不足
     * 修改数据: 无需修改
     *
     * @author jiayan
     * @version 0.0.1 8/16/21
     * @since 0.0.1 8/16/21
     */
    @DataPrepare
    @Test
    void createOrder_stockinsufficient_fail() throws ExecutionException, InterruptedException {
        long tenantId=1L,customerId = 2L,skuId1=3L,skuId2=4L;
        BigDecimal sku1Qty=BigDecimal.valueOf(1L);
        BigDecimal sku2Qty=BigDecimal.valueOf(6L);
        //查询当前基线库存
        Stock sku1CurrentQty = stockMapper.selectOne(new QueryWrapper<Stock>().lambda().eq(Stock::getSkuId, skuId1));
//        assertEquals(sku1CurrentQty.getQty(),Long.valueOf(3L));
        Stock sku2CurrentQty = stockMapper.selectOne(new QueryWrapper<Stock>().lambda().eq(Stock::getSkuId, skuId2));
//        assertEquals(sku2CurrentQty.getQty(),Long.valueOf(4L));

        //库存不足
        ResultDto<String> result = createOrder(tenantId, customerId, Arrays.asList(new Pair<>(skuId1, sku1Qty), new Pair<>(skuId2, sku2Qty)));
        assertFalse(result.isSuccess());

        //检查库存，期望原先时相同的，库存没变
        Runnable runnable =
                () -> {
                    Stock currentQty = stockMapper.selectOne(new QueryWrapper<Stock>().lambda().eq(Stock::getSkuId, skuId1));
                    assertEquals(currentQty.getQty(), BigDecimal.valueOf(3L));
                    currentQty = stockMapper.selectOne(new QueryWrapper<Stock>().lambda().eq(Stock::getSkuId, skuId2));
                    assertEquals(currentQty.getQty(), BigDecimal.valueOf(4L));
                };
        CompletableFuture<Void> cf = CompletableFuture.runAsync(runnable);
        cf.get();

    }


    /**
     * 场景：当用户是block状态，无法创建订单
     * @author jiayan
     * @version 0.0.1 8/30/21
     * @since 0.0.1 8/30/21
     */
    @DataPrepare
    @Test
    @Transactional
    public void createOrder_blockedUser_fail() throws ExecutionException, InterruptedException {
        long tenantId=1L,customerId = 1L,skuId1=3L,skuId2=4L;
        BigDecimal sku1Qty=BigDecimal.valueOf(1L);
        BigDecimal sku2Qty=BigDecimal.valueOf(1L);
        assertThrows(IllegalCustomerStatusException.class,()-> createOrder(tenantId, customerId,Arrays.asList(new Pair<>(skuId1,sku1Qty),new Pair<>(skuId2,sku2Qty))));
    }

    /**
     * 场景：当前用户未支付的订单超过5个，无法创建订单
     *
     * @author jiayan
     * @version 0.0.1 8/16/21
     * @since 0.0.1 8/16/21
     */
    @DataPrepare
    @Test
    @Transactional
    void createOrder_payingCountExceed_fail() throws ExecutionException, InterruptedException {
        long tenantId=1L,customerId = 2L,skuId1=3L,skuId2=4L;
        BigDecimal sku1Qty=BigDecimal.valueOf(1L);
        BigDecimal sku2Qty=BigDecimal.valueOf(1L);
        assertThrows(IllegalStateException.class,()-> createOrder(tenantId, customerId,Arrays.asList(new Pair<>(skuId1,sku1Qty),new Pair<>(skuId2,sku2Qty))));
    }

    private ResultDto<String> createOrder(long tenantId, long customerId, List<Pair<Long,BigDecimal>> skuAndQtys) {
        BillOrderCreatingRequestDTO billOrderCreatingDTO = new BillOrderCreatingRequestDTO();
        billOrderCreatingDTO.setOrderAddress("new");
        billOrderCreatingDTO.setTenantId(tenantId);
        billOrderCreatingDTO.setCustomerId(customerId);
        ArrayList<BillOrderCreatingRequestDTO.ItemDTO> details = new ArrayList<BillOrderCreatingRequestDTO.ItemDTO>();
        billOrderCreatingDTO.setDetails(details);

        for (Pair<Long,BigDecimal> skuAndQty :skuAndQtys) {
            BillOrderCreatingRequestDTO.ItemDTO detail =  new BillOrderCreatingRequestDTO.ItemDTO();
            detail.setSkuId(skuAndQty.getKey());
            detail.setQty(skuAndQty.getValue());
//            detail.setTenantId(tenantId);
            details.add(detail);
        }
        return orderService.createOrder(billOrderCreatingDTO);
    }
}