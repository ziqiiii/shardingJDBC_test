package com.ziqi.shardingjdbc.demo.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * create by ziqi.zhang on 2019/10/25
 */
@Component
public class StandTablePreciseAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {

        long id = preciseShardingValue.getValue();
        if ((id & 1) == 1){
            return (String) collection.toArray()[0];
        }else {
            return (String) collection.toArray()[1];
        }
    }
}