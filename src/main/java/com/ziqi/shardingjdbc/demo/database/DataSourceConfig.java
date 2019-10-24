package com.ziqi.shardingjdbc.demo.database;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.ziqi.shardingjdbc.demo.config.TableShardingAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by ziqi.zhang on 2019/10/12
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    private Database0Config database0Config;


    @Autowired
    private TableShardingAlgorithm tableShardingAlgorithm;

    @Value("${ziqi.sharding.tables.logic-table-name}")
    private String logicTable;

    @Value("${ziqi.sharding.tables.sharding-column}")
    private String shardingColumn;

    @Value("#{'${ziqi.sharding.tables.actual-table-name.list}'.split(',')}")
    private List<String> actualTables;

    @Bean
    public DataSource getDataSource() throws SQLException {
        return buildDataSource();
    }

    private DataSource buildDataSource() throws SQLException {
        //分库设置
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        //添加两个数据库database0和database1
        dataSourceMap.put(database0Config.getDatabaseName(), database0Config.createDataSource());
//        dataSourceMap.put(database1Config.getDatabaseName(), database1Config.createDataSource());
        //设置默认数据库
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, database0Config.getDatabaseName());


        //分表设置，大致思想就是将查询虚拟表Goods根据一定规则映射到真实表中去
        //Goods的分表规则
        TableRule orderTableRule = TableRule.builder(logicTable)
                .actualTables(actualTables)
//                .actualTables(Arrays.asList("goods_0", "goods_1"))
                .dataSourceRule(dataSourceRule)
                .build();


        //分库分表策略
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))
//                .databaseShardingStrategy(new DatabaseShardingStrategy("goods_id", databaseShardingAlgorithm))
//                .tableShardingStrategy(new TableShardingStrategy("goods_type", tableShardingAlgorithm)).build();
                .tableShardingStrategy(new TableShardingStrategy(shardingColumn, tableShardingAlgorithm)).build();


        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return dataSource;
    }

}
