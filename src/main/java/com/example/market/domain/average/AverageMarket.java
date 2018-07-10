package com.example.market.domain.average;

import lombok.Data;

import javax.persistence.*;

/**
 * @author gaoyp
 * @create 2018/7/10  10:14
 * average交易所的比特币数据
 **/
@Data
@Table(name = "average_market")
@Entity
public class AverageMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cashName;
    private Long last;

}
