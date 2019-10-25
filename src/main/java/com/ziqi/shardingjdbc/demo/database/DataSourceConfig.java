package com.ziqi.shardingjdbc.demo.database;

import com.ziqi.shardingjdbc.demo.config.StandTablePreciseAlgorithm;
import com.ziqi.shardingjdbc.demo.config.StandTableRangeAlgoeithm;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * create by ziqi.zhang on 2019/10/12
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    private Database0Config database0Config;

    @Autowired
    private StandTablePreciseAlgorithm standTablePreciseAlgorithm;

    @Autowired
    private StandTableRangeAlgoeithm standTableRangeAlgoeithm;

    @Value("${spring.shardingsphere.sharding.tables.goods.logicTable}")
    private String logicTable;

    @Value("${spring.shardingsphere.sharding.tables.goods.tableStrategy.standard.shardingColumn}")
    private String shardingColumn;

//    @Value("#{'${spring.shardingsphere.sharding.tables.actual-table-name.list}'.split(',')}")
//    private List<String> actualTables;


    @Value("${spring.shardingsphere.sharding.tables.goods.actualDataNodes}")
    private String goodsDetailActualDataNodes;

    @Bean
    public DataSource getDataSource() throws SQLException {
        return buildDataSource();
    }

    //分库设置
    private Map<String, DataSource> getDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
//        DruidDataSource dataSource1 = new DruidDataSource();
//        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource1.setUrl("jdbc:mysql://49.234.234.31:3306/shard_order_0");
//        dataSource1.setUsername("root");
//        dataSource1.setPassword("root");
//        dataSourceMap.put("shard_order_0", dataSource1);

        //添加两个数据库database0和database1
        dataSourceMap.put(database0Config.getDatabaseName(), database0Config.createDataSource());
//        dataSourceMap.put(database1Config.getDatabaseName(), database1Config.createDataSource());

        return dataSourceMap;
    }

    private TableRuleConfiguration getTableRuleConfiguration() {
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(logicTable, goodsDetailActualDataNodes);
        tableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(shardingColumn,
                standTablePreciseAlgorithm, standTableRangeAlgoeithm));
        return tableRuleConfiguration;
    }

    private DataSource buildDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getBindingTableGroups().add(logicTable);
        //配置goods表规则
        shardingRuleConfig.getTableRuleConfigs().add(getTableRuleConfiguration());

        shardingRuleConfig.setDefaultDataSourceName("database1");
        return ShardingDataSourceFactory.createDataSource(getDataSourceMap(), shardingRuleConfig, new Properties());

    }

}
