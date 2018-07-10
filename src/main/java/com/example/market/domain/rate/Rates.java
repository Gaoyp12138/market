package com.example.market.domain.rate;

import lombok.Data;

/**
 * @Author: Gaoyp
 * @Description:
 * @Date: Create in 下午2:31 2018/7/7
 * @Modified By:
 */
@Data
public class Rates {

    private CNYDetail CNY;
    private HKDDetail HKD;
    private KRWDetail KRW;
    private JPYDetail JPY;
}
