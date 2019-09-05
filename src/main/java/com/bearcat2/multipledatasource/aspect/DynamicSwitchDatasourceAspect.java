package com.bearcat2.multipledatasource.aspect;

import com.bearcat2.multipledatasource.annotation.DynamicSwitchDatasource;
import com.bearcat2.multipledatasource.entity.DatasourceSwitchThreadLocal;
import com.bearcat2.multipledatasource.enumeration.DatasourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <p> Description: 动态切换数据源切面 </p>
 * <p> Title: DynamicSwitchDatasourceAspect </p>
 * <p> Create Time: 2019/9/3 16:42 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
@Slf4j
@Aspect
@Order(-1)
@Component
public class DynamicSwitchDatasourceAspect {

    /** 切点表达式,找出所有service包及其后代包下所有类的所有方法 */
    @Pointcut("execution(* com.bearcat2.multipledatasource.service..*(..))")
    public void pointcut() {}

    /**
     * 在业务方法调用之前执行 先判断访问的目标方法上是否标记了DynamicSwitchDatasource注解,有则取出注解值直接返回
     * <p>
     * 没有再判断访问的目标上是否标记注解有则返回如果访问的目标方法和目标类上都没有标记注解,则数据源默认设置为 DatasourceEnum.DS1
     *
     * @param joinPoint 连接点
     */
    @Before("pointcut()")
    public void beforeSwitchDatasource(JoinPoint joinPoint) {
        //获得当前访问的目标方法
        Method targetMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (targetMethod.isAnnotationPresent(DynamicSwitchDatasource.class)) {
            DynamicSwitchDatasource annotation = targetMethod.getAnnotation(DynamicSwitchDatasource.class);
            DatasourceSwitchThreadLocal.set(annotation.value());
            return;
        }

        //获得当前访问的目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        if (targetClass.isAnnotationPresent(DynamicSwitchDatasource.class)) {
            DynamicSwitchDatasource annotation = targetClass.getAnnotation(DynamicSwitchDatasource.class);
            DatasourceSwitchThreadLocal.set(annotation.value());
            return;
        }

        // 走到这程序没有返回,说明目标方法和目标类上都没有 DynamicSwitchDatasource 注解,使用DS1作为默认的数据源
        DatasourceSwitchThreadLocal.set(DatasourceEnum.DS1);
    }

    /**
     * 在业务方法执行完毕之后执行,无论业务方法是否抛出异常 .清除设置的默认数据源
     */
    @After("pointcut()")
    public void afterSwitchDatasource() {
        DatasourceSwitchThreadLocal.remove();
    }
}
