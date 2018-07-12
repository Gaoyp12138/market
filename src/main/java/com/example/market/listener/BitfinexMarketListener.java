package com.example.market.listener;

import com.example.market.common.*;
import com.example.market.dao.bitfinex.BitfinexMarketDao;
import com.example.market.domain.bitfinex.BitfinexMarket;
import com.example.market.service.RatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gaoyp
 * @create 2018/7/12  11:25
 **/
@Component
@Slf4j
public class BitfinexMarketListener {

    @Value(("${bitfinex_market_url}"))
    private String url;

    @Resource
    private BitfinexMarketDao bitfinexMarketDao;
    
    @Resource
    private RatesService ratesService;

    @Scheduled(fixedRate = 1000 * 10)
    public void bitstampListener() {
        try {
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            log.info("bitstamp market开始监听...");
            String result = HttpUtils.sendGet(url);
            BitfinexMarket bitfinexMarket = (BitfinexMarket) JsonUtil.fromJson(result, BitfinexMarket.class);
            if (null != bitfinexMarket) {
                String last = bitfinexMarket.getLast_price();
                BitfinexMarket btcusd = bitfinexMarketDao.findByCashName(Const.USD.getFullName());
                if (VerifyNumUtil.isNumber(last)) {
                    if (null != btcusd) {
                        btcusd.setCurrentTime(currentTime);
                        btcusd.setLast_price(last);
                        bitfinexMarketDao.save(btcusd);
                    } else {
                        bitfinexMarket.setCurrentTime(currentTime);
                        bitfinexMarket.setCashName(Const.USD.getFullName());
                        bitfinexMarket.setCoinType(Coin.BTC.getCoinType());
                        bitfinexMarketDao.save(bitfinexMarket);
                    }
                    log.info(" bitfinex market 获取成功...");
                } else {
                    log.error("非法字符，本次不更新行情...");
                    return;
                }
            } else {
                log.error(" bitfinex market 获取失败...");
                return;
            }
        } catch (Exception e) {
            log.error(" bitfinex market 获取异常...");
        }
    }


    @Scheduled(fixedRate = 1000 * 10)
    public void getMarket(){
        getCoinCash(Const.CNY.getFullName(),Coin.BTC.getCoinType());
        getCoinCash(Const.KRW.getFullName(),Coin.BTC.getCoinType());
        getCoinCash(Const.JPY.getFullName(),Coin.BTC.getCoinType());
        getCoinCash(Const.HKD.getFullName(),Coin.BTC.getCoinType());
    }

    public void getCoinCash(String fullName, String coinType) {
        try {
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BitfinexMarket btcusd = bitfinexMarketDao.findByCashName(Const.USD.getFullName());
            if (null != btcusd) {
                String last = btcusd.getLast_price();
                String rate = null;
                switch (fullName) {
                    case "Chinese Yuan":
                        rate = ratesService.getCNYRate();
                        break;
                    case "Hong Kong Dollar":
                        rate = ratesService.getHKDRate();
                        break;
                    case "South Korean Won":
                        rate = ratesService.getKRWRate();
                        break;
                    case "Japanese Yen":
                        rate = ratesService.getJPYRate();
                        break;
                }

                if (null == rate) {
                    log.error("汇率数据为空...");
                    return;
                } else {
                    BigDecimal coincash = ArithmeticUtils.multiply(rate, last);
                    BitfinexMarket bitfinexMarket = bitfinexMarketDao.findByCashName(fullName);
                    if (null != bitfinexMarket) {
                        bitfinexMarket.setLast_price(coincash.toString());
                        bitfinexMarketDao.save(bitfinexMarket);
                    } else {
                        BitfinexMarket bitfinexMarket1 = new BitfinexMarket();
                        bitfinexMarket1.setLast_price(coincash.toString());
                        bitfinexMarket1.setCoinType(coinType);
                        bitfinexMarket1.setCashName(fullName);
                        bitfinexMarket1.setCurrentTime(currentTime);
                        bitfinexMarketDao.save(bitfinexMarket1);
                    }
                }
            } else {
                log.error("获取失败...");
                return;
            }
        } catch (Exception e) {

        }
    }

}
