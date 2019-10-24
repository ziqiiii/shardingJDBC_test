package com.ziqi.shardingjdbc.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * create by ziqi.zhang on 2019/10/11
 */

@Entity
@Table(name="goods")
@Data
public class Goods {
    @Id
    private Long goodsId;

    private String goodsName;

    private Long goodsType;
}
