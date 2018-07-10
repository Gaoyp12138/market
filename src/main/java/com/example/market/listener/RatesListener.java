package com.example.market.listener;

import com.example.market.common.Const;
import com.example.market.common.HttpUtils;
import com.example.market.common.JsonUtil;
import com.example.market.common.VerifyNumUtil;
import com.example.market.dao.CNYDetailDao;
import com.example.market.dao.HKDDetailDao;
import com.example.market.dao.JPYDetailDao;
import com.example.market.dao.KRWDetailDao;
import com.example.market.domain.rate.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Gaoyp
 * @Description:
 * @Date: Create in 下午2:43 2018/7/7
 * @Modified By:
 */
@Component
@Slf4j
public class RatesListener {

    @Resource
    private CNYDetailDao cnyDetailDao;
    @Resource
    private KRWDetailDao krwDetailDao;
    @Resource
    private JPYDetailDao jpyDetailDao;
    @Resource
    private HKDDetailDao hkdDetailDao;


    @Value("${global_rate_url}")
    private String url;


    @Scheduled(cron = "0 0 24 * * ?")
    public void getCNYRates(){

    }
    @Scheduled(cron = "0 0 24 * * ?")
    public void getHKDRates(){

    }
    @Scheduled(cron = "0 0 24 * * ?")
    public void getJPYRates(){

    }
    @Scheduled(cron = "0 0 24 * * ?")
    public void getKRWRates(){

    }


    @Scheduled(cron = "0 0 24 * * ?")
    public void getRates(){

        try {
            log.info("全球汇率开始监听...");
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String result = HttpUtils.sendGet(url);
            ExchangeRate exchangeRate = (ExchangeRate)JsonUtil.fromJson(result, ExchangeRate.class);
            if (null == exchangeRate){
                log.error("获取全球汇率失败...");
                return;
            }else {

                String cnyName = exchangeRate.getRates().getCNY().getName();
                String cnyRate = exchangeRate.getRates().getCNY().getRate();

                String hkdName = exchangeRate.getRates().getHKD().getName();
                String hkdRate = exchangeRate.getRates().getHKD().getRate();

                String jpyName = exchangeRate.getRates().getJPY().getName();
                String jpyRate = exchangeRate.getRates().getJPY().getRate();

                String krwName = exchangeRate.getRates().getKRW().getName();
                String krwRate = exchangeRate.getRates().getKRW().getRate();


                CNYDetail cny = cnyDetailDao.findByName(Const.CNY.getFullName());
                HKDDetail hkd = hkdDetailDao.findByName(Const.HKD.getFullName());
                JPYDetail jpy = jpyDetailDao.findByName(Const.HKD.getFullName());
                KRWDetail krw = krwDetailDao.findByName(Const.KRW.getFullName());

                if (VerifyNumUtil.isNumber(cnyRate) && VerifyNumUtil.isNumber(hkdRate) && VerifyNumUtil.isNumber(jpyRate) && VerifyNumUtil.isNumber(krwRate)){
                    if (null != cny && null != hkd && null != jpy && null != krw){
                        cny.setDate(currentTime);
                        cny.setName(cnyName);
                        cny.setRate(cnyRate);
                        cnyDetailDao.save(cny);

                        hkd.setDate(currentTime);
                        hkd.setName(hkdName);
                        hkd.setRate(hkdRate);
                        hkdDetailDao.save(hkd);

                        jpy.setDate(currentTime);
                        jpy.setName(jpyName);
                        jpy.setRate(jpyRate);
                        jpyDetailDao.save(jpy);

                        krw.setDate(currentTime);
                        krw.setName(krwName);
                        krw.setRate(krwRate);
                        krwDetailDao.save(krw);

                    }else {
                        exchangeRate.getRates().getCNY().setDate(currentTime);
                        exchangeRate.getRates().getHKD().setDate(currentTime);
                        exchangeRate.getRates().getJPY().setDate(currentTime);
                        exchangeRate.getRates().getKRW().setDate(currentTime);
                        cnyDetailDao.save(exchangeRate.getRates().getCNY());
                        hkdDetailDao.save(exchangeRate.getRates().getHKD());
                        jpyDetailDao.save(exchangeRate.getRates().getJPY());
                        krwDetailDao.save(exchangeRate.getRates().getKRW());
                    }
                }else {
                    log.error("获取全球汇率含有异常字符，本次不更新");
                    return;
                }

                log.info("全球汇率获取正常...");

            }
        }catch (Exception e){
            log.error("获取全球汇率异常...", e);
        }

    }
}
