package com.bearcat2.multipledatasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> Description: 多数据源属性文件 </p>
 * <p> Title: MultipleDatasourceProperties </p>
 * <p> Create Time: 2019/9/3 15:38 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
@Data
@Component
@ConfigurationProperties("spring.multiple.datasource")
public class MultipleDatasourceProperties {

    private CommonDatasourceProperties ds1;

    private CommonDatasourceProperties ds2;

    private Long maxWait;

    private Long timeBetweenEvictionRunsMillis;

    private Long minEvictableIdleTimeMillis;

    private String validationQuery;

    private Boolean testWhileIdle;

    private Boolean testOnBorrow;

    private Boolean testOnReturn;

    private Boolean poolPreparedStatements;

    private Integer maxPoolPreparedStatementPerConnectionSize;

    private String filters;

    private Boolean asyncInit;

    @Data
    public static class CommonDatasourceProperties {

        private String username;

        private String password;

        private String url;

        private String driverClassName;

        private Integer initialSize;

        private Integer minIdle;

        private Integer maxActive;
    }
}
