package com.example.market.dao.coinmarketcap;

import com.example.market.domain.coinmarketcap.USDDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gaoyp
 * @create 2018/7/11  14:54
 **/
@Repository
public interface USDDetailsDao extends CrudRepository<USDDetails,Long>{

    USDDetails findByCoinType(String coinType);

}
