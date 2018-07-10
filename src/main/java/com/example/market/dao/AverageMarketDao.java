package com.example.market.dao;

import com.example.market.domain.average.AverageMarket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gaoyp
 * @create 2018/7/10  13:47
 **/
@Repository
public interface AverageMarketDao extends CrudRepository<AverageMarket,Long>{

    AverageMarket findByCashName(String cashName);


}
