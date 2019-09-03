package com.bearcat2.multipledatasource.entity;

import com.bearcat2.multipledatasource.enumeration.DatasourceEnum;

/**
 * <p> Description: 数据源切换的本地线程类 </p>
 * <p> Title: SwitchThreadLocal </p>
 * <p> Create Time: 2019/9/3 15:58 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
public class DatasourceSwitchThreadLocal {

    private static ThreadLocal<DatasourceEnum> datasourceSwitchThreadLocal = new ThreadLocal<>();

    public static void set(DatasourceEnum datasourceEnum) {
        datasourceSwitchThreadLocal.set(datasourceEnum);
    }

    public static DatasourceEnum get() {
        return datasourceSwitchThreadLocal.get();
    }

    public static void remove(){
        datasourceSwitchThreadLocal.remove();
    }
}
