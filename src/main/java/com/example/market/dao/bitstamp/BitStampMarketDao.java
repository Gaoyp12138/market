package com.example.market.dao.bitstamp;

import com.example.market.domain.bitstamp.BitstampMarket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gaoyp
 * @create 2018/7/11  18:10
 **/
@Repository
public interface BitStampMarketDao extends CrudRepository<BitstampMarket,Long>{

    BitstampMarket findByCashName(String cashName);

}
