package com.example.market.dao.average;

import com.example.market.domain.rate.KRWDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gaoyp
 * @Description:
 * @Date: Create in 下午6:59 2018/7/7
 * @Modified By:
 */
@Repository
public interface KRWDetailDao extends CrudRepository<KRWDetail,Long>{

    KRWDetail findByName(String name);

}
