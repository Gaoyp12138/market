package com.example.market.listener;

import com.example.market.common.*;
import com.example.market.dao.AverageMarketDao;
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

    @Scheduled(fixedRate = 1000 * 10)
    public void getBTCCNY(){
        averageListener(Const.CNY.getAbbreviation(),Const.CNY.getFullName());
    }

    @Scheduled(fixedRate = 1000 * 10)
    public void getBTCHKD(){
        averageListener(Const.HKD.getAbbreviation(),Const.HKD.getFullName());
    }

    @Scheduled(fixedRate = 1000 * 10)
    public void getBTCJPY(){
        averageListener(Const.JPY.getAbbreviation(),Const.JPY.getFullName());
    }
    @Scheduled(fixedRate = 1000 * 10)
    public void getBTCKRW(){
        averageListener(Const.KRW.getAbbreviation(),Const.KRW.getFullName());
    }

    public void averageListener(String abbreviation,String fullName){

        try {
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            log.info("average market  " + Coin.BTC.getCoinType() + abbreviation + "  开始监听...");
            String result = HttpUtils.sendGet(url + Coin.BTC.getCoinType() + abbreviation);
             AverageMarket averageMarket= (AverageMarket)JsonUtil.fromJson(result, AverageMarket.class);
             if (null != averageMarket){
                 String last = averageMarket.getLast();
                 AverageMarket btccash = averageMarketDao.findByCashName(fullName);
                 if (VerifyNumUtil.isNumber(last)){
                     if (null != btccash){
                         btccash.setLast(last);
                         btccash.setCurrentTime(currentTime);
                         averageMarketDao.save(btccash);
                     }else {
                         averageMarket.setCurrentTime(currentTime);
                         averageMarket.setCashName(fullName);
                         averageMarketDao.save(averageMarket);
                     }
                 }else {
                     log.error("非法字符，本次不更新行情...");
                     return;
                 }
             }else {
                 log.error("average market  " + Coin.BTC.getCoinType() + abbreviation +"  获取失败...");
                 return;
             }
        }catch (Exception e){
            log.error("average market  " + Coin.BTC.getCoinType() + abbreviation +"  获取异常...");
        }
    }
}
