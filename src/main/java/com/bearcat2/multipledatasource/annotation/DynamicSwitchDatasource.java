package com.bearcat2.multipledatasource.annotation;

import com.bearcat2.multipledatasource.enumeration.DatasourceEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * <p> Description: 动态切换数据源注解  </p>
 * <p> Title: DynamicSwitchDatasource </p>
 * <p> Create Time: 2019/9/3 16:40 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicSwitchDatasource {

    DatasourceEnum value() default DatasourceEnum.DS1;
}
