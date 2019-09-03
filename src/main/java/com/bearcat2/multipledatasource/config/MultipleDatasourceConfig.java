package com.bearcat2.multipledatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.bearcat2.multipledatasource.enumeration.DatasourceEnum;
import com.bearcat2.multipledatasource.properties.MultipleDatasourceProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * <p> Description: 多数据源配置 </p>
 * <p> Title: MultipleDatasourceConfig </p>
 * <p> Create Time: 2019/9/3 15:44 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
@Configuration
public class MultipleDatasourceConfig {

    @Autowired
    private MultipleDatasourceProperties multipleDatasourceProperties;

    @Bean
    public DruidDataSource ds1() {
        return this.buildDruidDataSource(multipleDatasourceProperties.getDs1());
    }

    @Bean
    public DruidDataSource ds2() {
        return this.buildDruidDataSource(multipleDatasourceProperties.getDs2());
    }

    private DruidDataSource buildDruidDataSource(MultipleDatasourceProperties.CommonDatasourceProperties datasourceProperties) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(datasourceProperties.getUrl());
        druidDataSource.setUsername(datasourceProperties.getUsername());
        druidDataSource.setPassword(datasourceProperties.getPassword());
        druidDataSource.setDriverClassName(datasourceProperties.getDriverClassName());


        druidDataSource.setInitialSize(datasourceProperties.getInitialSize());
        druidDataSource.setMinIdle(datasourceProperties.getMinIdle());
        druidDataSource.setMaxActive(datasourceProperties.getMaxActive());
        druidDataSource.setMaxWait(multipleDatasourceProperties.getMaxWait());

        druidDataSource.setTimeBetweenEvictionRunsMillis(multipleDatasourceProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(multipleDatasourceProperties.getMinEvictableIdleTimeMillis());

        druidDataSource.setValidationQuery(multipleDatasourceProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(multipleDatasourceProperties.getTestWhileIdle());
        druidDataSource.setTestOnBorrow(multipleDatasourceProperties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(multipleDatasourceProperties.getTestOnReturn());

        druidDataSource.setPoolPreparedStatements(multipleDatasourceProperties.getPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(multipleDatasourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            druidDataSource.setFilters(multipleDatasourceProperties.getFilters());
        } catch (SQLException e) {
            // 将异常转型,向上抛出
            throw new RuntimeException(e);
        }
        druidDataSource.setAsyncInit(multipleDatasourceProperties.getAsyncInit());
        return druidDataSource;
    }

    @Bean
    public AbstractRoutingDataSource abstractRoutingDataSource() {
        AbstractRoutingDataSource abstractRoutingDataSource = new DynamicDatasource();
        HashMap<Object, Object> datasourceMap = new HashMap<>();
        datasourceMap.put(DatasourceEnum.DS1, ds1());
        datasourceMap.put(DatasourceEnum.DS2, ds2());
        abstractRoutingDataSource.setTargetDataSources(datasourceMap);
        // 默认使用 ds1数据源
        abstractRoutingDataSource.setDefaultTargetDataSource(ds1());
        return abstractRoutingDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(AbstractRoutingDataSource abstractRoutingDataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(abstractRoutingDataSource);
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(AbstractRoutingDataSource abstractRoutingDataSource) {
        return new DataSourceTransactionManager(abstractRoutingDataSource);
    }
}
