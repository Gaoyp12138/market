package com.example.market.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author gaoyp
 * @create 2018/7/10  11:42
 **/
@Getter
@AllArgsConstructor
public enum Coin {

    BTC(1L,"BTC"),ETH(2L,"ETH");

    private Long status;
    private String coinType;

}
