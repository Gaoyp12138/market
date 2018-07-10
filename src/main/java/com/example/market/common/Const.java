package com.example.market.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Gaoyp
 * @Description:
 * @Date: Create in 下午2:45 2018/7/7
 * @Modified By:
 */
@Getter
@AllArgsConstructor
public enum Const {

    JPY(1L,"JPY","Japanese Yen"),USD(2L,"USD","United States Dollar"),CNY(3L,"CNY","Chinese Yuan"),
    KRW(4L,"KRW","South Korean Won"),HKD(5L,"HKD","Hong Kong Dollar");

    private Long status;
    private String abbreviation;
    private String fullName;




}
