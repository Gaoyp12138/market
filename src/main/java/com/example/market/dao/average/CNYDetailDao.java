package com.example.market.dao.average;

import com.example.market.domain.rate.CNYDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gaoyp
 * @Description:
 * @Date: Create in 下午6:59 2018/7/7
 * @Modified By:
 */
@Repository
public interface CNYDetailDao extends CrudRepository<CNYDetail,Long> {

    CNYDetail findByName(String name);

}
