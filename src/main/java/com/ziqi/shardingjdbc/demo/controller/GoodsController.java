package com.ziqi.shardingjdbc.demo.controller;

import com.ziqi.shardingjdbc.demo.entity.Goods;
import com.ziqi.shardingjdbc.demo.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by ziqi.zhang on 2019/10/12
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping("save")
    public String save() {
        for (int i = 1; i <= 30; i++) {

            Goods goods = new Goods();
//            Number n = keyGenerator.generateKey();
//            System.out.println("keyGenerator:"+n);
//            goods.setGoodsId((long) n);
            goods.setGoodsId((long) i);
            goods.setGoodsName("z" + i);
            goods.setGoodsType((long) (i + 1));

            long time1 = System.currentTimeMillis();
            System.out.println("读取cha入一条数据开始时间：" + time1);
            goodsRepository.save(goods);

            long time2 = System.currentTimeMillis();
            System.out.println("读取插入一条数据结束时间：" + time2);
            long bet = time2 - time1;
            System.out.println("读取插入一条数据花费时间：" + bet);

        }
        return "success";
    }


}
