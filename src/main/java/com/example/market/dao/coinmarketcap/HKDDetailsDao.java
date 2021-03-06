package com.example.market.dao.coinmarketcap;

import com.example.market.domain.coinmarketcap.HKDDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gaoyp
 * @create 2018/7/11  14:55
 **/
@Repository
public interface HKDDetailsDao extends CrudRepository<HKDDetails,Long> {

    HKDDetails findByCoinType(String coinType);

}
