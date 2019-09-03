package com.bearcat2.multipledatasource.config;

import com.bearcat2.multipledatasource.entity.DatasourceSwitchThreadLocal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <p> Description: 动态数据源配置 </p>
 * <p> Title: DynamicDatasource </p>
 * <p> Create Time: 2019/9/3 15:57 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
public class DynamicDatasource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DatasourceSwitchThreadLocal.get();
    }
}
