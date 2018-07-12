package com.example.market.dao.bitfinex;

import com.example.market.domain.bitfinex.BitfinexMarket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gaoyp
 * @create 2018/7/12  11:23
 **/
@Repository
public interface BitfinexMarketDao extends CrudRepository<BitfinexMarket,Long>{

    BitfinexMarket findByCashName(String cashName);

}
