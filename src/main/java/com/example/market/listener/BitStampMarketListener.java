package com.example.market.listener;

import com.example.market.common.*;
import com.example.market.dao.average.CNYDetailDao;
import com.example.market.dao.bitstamp.BitStampMarketDao;
import com.example.market.domain.average.AverageMarket;
import com.example.market.domain.bitstamp.BitstampMarket;
import com.example.market.domain.rate.CNYDetail;
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
 * @create 2018/7/11  18:05
 **/
@Component
@Slf4j
public class BitStampMarketListener {

    @Value("${bitstamp_market_url}")
    private String url;

    @Resource
    private BitStampMarketDao bitStampMarketDao;

    @Resource
    private CNYDetailDao cnyDetailDao;

    @Resource
    private RatesService ratesService;

    @Scheduled(fixedRate = 1000 * 10)
    public void bitstampListener() {
        try {
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            log.info("bitstamp market开始监听...");
            String result = HttpUtils.sendGet(url);
            BitstampMarket bitstampMarket = (BitstampMarket) JsonUtil.fromJson(result, BitstampMarket.class);
            if (null != bitstampMarket) {
                String last = bitstampMarket.getLast();
                BitstampMarket btcusd = bitStampMarketDao.findByCashName(Const.USD.getFullName());
                if (VerifyNumUtil.isNumber(last)) {
                    if (null != btcusd) {
                        btcusd.setCurrentTime(currentTime);
                        btcusd.setLast(last);
                        bitStampMarketDao.save(btcusd);
                    } else {
                        bitstampMarket.setCurrentTime(currentTime);
                        bitstampMarket.setCashName(Const.USD.getFullName());
                        bitstampMarket.setCoinType(Coin.BTC.getCoinType());
                        bitStampMarketDao.save(bitstampMarket);
                    }
                    log.info("bitstamp market 获取成功...");
                } else {
                    log.error("非法字符，本次不更新行情...");
                    return;
                }
            } else {
                log.error("bitstamp market 获取失败...");
                return;
            }
        } catch (Exception e) {
            log.error("bitstamp market 获取异常...");
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
            BitstampMarket btcusd = bitStampMarketDao.findByCashName(Const.USD.getFullName());
            if (null != btcusd) {
                String last = btcusd.getLast();
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
                    BitstampMarket bitstampMarket = bitStampMarketDao.findByCashName(fullName);
                    if (null != bitstampMarket) {
                        bitstampMarket.setLast(coincash.toString());
                        bitStampMarketDao.save(bitstampMarket);
                    } else {
                        BitstampMarket bitstampMarket1 = new BitstampMarket();
                        bitstampMarket1.setLast(coincash.toString());
                        bitstampMarket1.setCoinType(coinType);
                        bitstampMarket1.setCashName(fullName);
                        bitstampMarket1.setCurrentTime(currentTime);
                        bitStampMarketDao.save(bitstampMarket1);
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
