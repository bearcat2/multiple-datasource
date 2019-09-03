package com.bearcat2.multipledatasource.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> Description: 配置系统所需数据源 </p>
 * <p> Title: DatasourceEnum </p>
 * <p> Create Time: 2019/9/3 19:48 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum DatasourceEnum {
    DS1("第一个数据源"),
    DS2("第二个数据源");

    /** 描述信息 */
    private String description;
}
