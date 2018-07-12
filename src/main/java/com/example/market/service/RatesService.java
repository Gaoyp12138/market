package com.example.market.service;

import com.example.market.common.Const;
import com.example.market.dao.average.CNYDetailDao;
import com.example.market.dao.average.HKDDetailDao;
import com.example.market.dao.average.JPYDetailDao;
import com.example.market.dao.average.KRWDetailDao;
import com.example.market.domain.rate.CNYDetail;
import com.example.market.domain.rate.HKDDetail;
import com.example.market.domain.rate.JPYDetail;
import com.example.market.domain.rate.KRWDetail;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gaoyp
 * @create 2018/7/12  10:40
 **/
@Service
@Slf4j
public class RatesService {

    @Resource
    private CNYDetailDao cnyDetailDao;

    @Resource
    private KRWDetailDao krwDetailDao;

    @Resource
    private HKDDetailDao hkdDetailDao;

    @Resource
    private JPYDetailDao jpyDetailDao;


    /**
     * 获取人民币汇率
     **/
    public String getCNYRate(){
        try {
            CNYDetail cnyDetail = cnyDetailDao.findByName(Const.CNY.getFullName());
            if (null != cnyDetail){
                return cnyDetail.getRate();
            }else {
                return null;
            }
        }catch (Exception e){
            log.error("获取汇率异常...");
            return null;
        }
    }

    /**
     * 日元
     **/
    public String getJPYRate(){
        try {
            JPYDetail jpyDetail = jpyDetailDao.findByName(Const.JPY.getFullName());
            if (null != jpyDetail){
                return jpyDetail.getRate();
            }else {
                return null;
            }
        }catch (Exception e){
            log.error("获取汇率异常...");
            return null;
        }
    }

    /**
     * 韩元
     **/
    public String getKRWRate(){
        try {
            KRWDetail krwDetail = krwDetailDao.findByName(Const.KRW.getFullName());
            if (null != krwDetail){
                return krwDetail.getRate();
            }else {
                return null;
            }
        }catch (Exception e){
            log.error("获取汇率异常...");
            return null;
        }
    }

    /**
     * 港币
     **/
    public String getHKDRate(){
        try {
            HKDDetail hkdDetail = hkdDetailDao.findByName(Const.HKD.getFullName());
            if (null != hkdDetail){
                return hkdDetail.getRate();
            }else {
                return null;
            }
        }catch (Exception e){
            log.error("获取汇率异常...");
            return null;
        }
    }
}
