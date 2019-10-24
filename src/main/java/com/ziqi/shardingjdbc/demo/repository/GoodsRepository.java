package com.ziqi.shardingjdbc.demo.repository;

import com.ziqi.shardingjdbc.demo.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * create by ziqi.zhang on 2019/10/18
 */
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select g from Goods g")
    List<Object> selectAll();

    @Query("select g from Goods g where g.goodsId =:id and g.goodsType =:type")
    Goods selectsome(@Param("id") Long id, @Param("type")Long type);

    List<Goods> findAllByGoodsIdBetween(Long goodsId1,Long goodsId2);

    List<Goods> findAllByGoodsIdIn(List<Long> goodsIds);


    @Query("select g from Goods g where g.goodsId between :a  and :b ")
    List<Object> between1(@Param("a") Long a, @Param("b")Long b);

    @Query("select g from Goods g where g.goodsName in ('z1','z2','z3')")
    List<Object> selectIn();

    @Modifying
    @Transactional
    @Query("update Goods g set g.goodsName =:name where g.goodsId =:id")
    int update(@Param("id") Long id, @Param("name")String name);

    @Modifying
    @Transactional
    @Query("delete from Goods")
    int delete1();

    @Modifying
    @Transactional
    @Query("delete from Goods g where g.goodsType <= 2")
    int delete2();

    //https://shardingsphere.apache.org/document/current/cn/features/sharding/use-norms/sql/
    @Query("select g.goodsName, sum(g.goodsType) from Goods g group by g.goodsName")
    List<Object> groupBy();

    @Query("select g.goodsName, sum(g.goodsType) as res from Goods g group by g.goodsName order by res desc ")
    List<Object> orderBy();

    //https://juejin.im/post/5c146306f265da612d192878
    //limit
    @Query(nativeQuery=true, value ="select g.goods_name, sum(g.goods_type) as res from Goods g group by g.goods_name order by res desc limit 0,3 ")
    List<Object> orderBy2();


    @Query("select  max(g.goodsType) as max from Goods g ")
    long max();

    @Query("select  min(g.goodsType) as min from Goods g ")
    long min();

    @Query("select g.goodsId, avg(g.goodsType) as avg1 from Goods g group by g.goodsName order by avg1 desc ")
    List<Object> avg();

    @Modifying
    @Transactional
    @Query(nativeQuery=true, value ="truncate Goods")
    void truncate();




    //不能分片
    @Modifying
    @Transactional
    @Query(nativeQuery=true, value = "insert into Goods values(2,'ziqi',2)")
    int insert();

    //com.dangdang.ddframe.rdb.sharding.parsing.parser.exception.SQLParsingUnsupportedException: Not supported token 'DISTINCT'.
    //不支持。
    @Query(nativeQuery=true, value ="select distinct g.goodsName from Goods g")
    List<Object> distinct1();

    //com.dangdang.ddframe.rdb.sharding.parsing.parser.exception.SQLParsingUnsupportedException: Not supported token 'IDENTIFIER'.
    @Modifying
    @Transactional
    @Query(nativeQuery=true, value ="ALTER Goods g add col int(11)")
    void alter();

    //com.dangdang.ddframe.rdb.sharding.parsing.parser.exception.SQLParsingUnsupportedException: Not supported token 'IDENTIFIER'.
    @Modifying
    @Transactional
    @Query(nativeQuery=true, value ="drop Goods g")
    void drop();


    //com.dangdang.ddframe.rdb.sharding.parsing.parser.exception.SQLParsingUnsupportedException: Not supported token 'INDEX'.
    @Modifying
    @Transactional
    @Query(nativeQuery=true, value ="CREATE INDEX idxname ON Goods")
    void index();
}
