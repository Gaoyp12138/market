package com.example.market.dao;

import com.example.market.domain.rate.JPYDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gaoyp
 * @Description:
 * @Date: Create in 下午6:57 2018/7/7
 * @Modified By:
 */
@Repository
public interface JPYDetailDao extends CrudRepository<JPYDetail,Long> {

    JPYDetail findByName(String name);

}
