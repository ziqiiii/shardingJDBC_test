package com.ziqi.shardingjdbc.demo.controller;

import com.ziqi.shardingjdbc.demo.entity.Goods;
import com.ziqi.shardingjdbc.demo.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

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


    @GetMapping("select")
    public List<Goods> select() {
        return goodsRepository.findAll();
    }

    @GetMapping("selectAll")
    public List<Object> selectAll() {
        List<Object> list = goodsRepository.selectAll();
        return list;
    }

    @GetMapping("selectsome")
    public Goods selectsome() {
        Goods goods = goodsRepository.selectsome((long)1,(long)1);
        return goods;
    }

    @GetMapping("update")
    public String update() {
        for (int i = 1; i <= 10; i++) {
            Goods goods = new Goods();
            goods.setGoodsId((long) i);
            goods.setGoodsName("ziqi" + i);
            goods.setGoodsType((long) (i + 1));
            goodsRepository.save(goods);

//            int res = goodsRepository.update((long) i,"ziqi" + i);
        }
        return "success";
    }


    @GetMapping("deleteAll")
    public void deleteAll() {
        goodsRepository.deleteAll();
    }

    @GetMapping("delete")
    public void delete() {
        Goods goods = new Goods();
        goods.setGoodsId((long) 1);
        goodsRepository.delete(goods);
    }

    @GetMapping("delete1")
    public void delete1() {
        goodsRepository.delete1();
    }

    @GetMapping("delete2")
    public void delete2() {
        goodsRepository.delete2();
    }

    @GetMapping("between")
    public Object between() {
        return goodsRepository.findAllByGoodsIdBetween(10L, 30L);
    }

    @GetMapping("between1")
    public List<Object> between1() {
        long a = 1;
        long b = 5;
        List<Object> list = goodsRepository.between1(a, b);
        return list;
    }

    @GetMapping("in")
    public Object in() {
        List<Long> goodsIds = new ArrayList<>();
        goodsIds.add(10L);
        goodsIds.add(15L);
        goodsIds.add(20L);
        goodsIds.add(25L);
        return goodsRepository.findAllByGoodsIdIn(goodsIds);
    }

    @GetMapping("in2")
    public List<Object> selectIn() {
        List<Object> list = goodsRepository.selectIn();
        return list;
    }


    @GetMapping("groupBy")
    public List<Object> groupBy() {
        return goodsRepository.groupBy();
    }

    @GetMapping("orderBy")
    public List<Object> orderBy() {
        return goodsRepository.orderBy();
    }

    @GetMapping("orderBy2")
    public List<Object> orderBy2() {
        return goodsRepository.orderBy2();
    }


    @GetMapping("max")
    public long max() {
        long goodsType = goodsRepository.max();
        return goodsType;
    }

    @GetMapping("min")
    public long min() {
        long goodsType = goodsRepository.min();
        return goodsType;
    }

    @GetMapping("avg")
    public List<Object> avg() {
        List<Object> list = goodsRepository.avg();
        return list;
    }

    @GetMapping("count")
    public long count() {
        long count1 = goodsRepository.count();
        return count1;
    }


    @GetMapping("truncate")
    public void truncate() {
        goodsRepository.truncate();
    }

    //com.dangdang.ddframe.rdb.sharding.parsing.parser.exception.SQLParsingUnsupportedException: Not supported token 'DISTINCT'.
    //不支持。
    @GetMapping("distinct1")
    public List<Object> distinct1() {
        List<Object> list = goodsRepository.distinct1();
        return list;
    }


    //不能分片
    @GetMapping("insert")
    public void insert() {
        goodsRepository.insert();
    }

    //com.dangdang.ddframe.rdb.sharding.parsing.parser.exception.SQLParsingUnsupportedException: Not supported token 'IDENTIFIER'.
    @GetMapping("alter")
    public void alter() {
        goodsRepository.alter();
    }

    //com.dangdang.ddframe.rdb.sharding.parsing.parser.exception.SQLParsingUnsupportedException: Not supported token 'IDENTIFIER'.
    @GetMapping("drop")
    public void drop() {
        goodsRepository.drop();
    }

    //com.dangdang.ddframe.rdb.sharding.parsing.parser.exception.SQLParsingUnsupportedException: Not supported token 'INDEX'.
    @GetMapping("index")
    public void index() {
        goodsRepository.index();
    }

}
