package com.example.market.listener;

import com.example.market.common.*;
import com.example.market.dao.average.AverageMarketDao;
import com.example.market.domain.average.AverageMarket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gaoyp
 * @create 2018/7/10  11:13
 **/
@Component
@Slf4j
public class AverageListener {

    @Value("${average_market_url}")
    private String url;

    @Resource
    private AverageMarketDao averageMarketDao;

//    @Scheduled(fixedRate = 1000 * 10)
    public void getBTCCash(){
        averageListener(Const.CNY.getAbbreviation(),Const.CNY.getFullName(),Coin.BTC.getCoinType());
        averageListener(Const.HKD.getAbbreviation(),Const.HKD.getFullName(),Coin.BTC.getCoinType());
        averageListener(Const.JPY.getAbbreviation(),Const.JPY.getFullName(),Coin.BTC.getCoinType());
        averageListener(Const.KRW.getAbbreviation(),Const.KRW.getFullName(),Coin.BTC.getCoinType());
        averageListener(Const.USD.getAbbreviation(),Const.USD.getFullName(),Coin.BTC.getCoinType());
    }

    public void averageListener(String abbreviation,String fullName,String coinType){

        try {
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            log.info("average market  " + coinType + abbreviation + "  开始监听...");
            String result = HttpUtils.sendGet(url + coinType + abbreviation);
             AverageMarket averageMarket= (AverageMarket)JsonUtil.fromJson(result, AverageMarket.class);
             if (null != averageMarket){
                 String last = averageMarket.getLast();
                 AverageMarket coincash = averageMarketDao.findByCashName(fullName);
                 if (VerifyNumUtil.isNumber(last)){
                     if (null != coincash){
                         coincash.setLast(last);
                         coincash.setCurrentTime(currentTime);
                         coincash.setCoinType(coinType);
                         averageMarketDao.save(coincash);
                     }else {
                         averageMarket.setCurrentTime(currentTime);
                         averageMarket.setCashName(fullName);
                         averageMarket.setCoinType(coinType);
                         averageMarketDao.save(averageMarket);
                     }
                     log.info("average market  " + coinType + abbreviation +"  获取成功...");
                 }else {
                     log.error("非法字符，本次不更新行情...");
                     return;
                 }
             }else {
                 log.error("average market  " + coinType + abbreviation +"  获取失败...");
                 return;
             }
        }catch (Exception e){
            log.error("average market  " + coinType + abbreviation +"  获取异常...");
        }
    }
}
