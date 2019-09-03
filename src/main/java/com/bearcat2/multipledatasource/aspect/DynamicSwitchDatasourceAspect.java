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

    /** 切点表达式,找出所有service包及其后代包下的所有方法 */
    @Pointcut("execution(* com.bearcat2.multipledatasource.service..*(..))")
    public void pointcut() {}

    /**
     * 在业务方法调用之前执行
     *
     * @param joinPoint 连接点
     */
    @Before("pointcut()")
    public void beforeSwitchDatasource(JoinPoint joinPoint) {
        //获得当前访问的目标类对象
        Class<?> targetClass = joinPoint.getTarget().getClass();
        //获得访问的方法名
        String methodName = joinPoint.getSignature().getName();
        //得到方法的参数的类型
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method targetMethod = null;
        try {
            targetMethod = targetClass.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            log.error("获取目标方法失败", e);
        }

        // 先使用目标方法上标记的 DynamicSwitchDatasource 注解，如果目标方法上没有则使用目标类上注解，如果两者都没有标记则使用默认的
        if (targetMethod != null && targetMethod.isAnnotationPresent(DynamicSwitchDatasource.class)) {
            DynamicSwitchDatasource annotation = targetMethod.getAnnotation(DynamicSwitchDatasource.class);
            DatasourceSwitchThreadLocal.set(annotation.value());
            return;
        }

        if (targetClass.isAnnotationPresent(DynamicSwitchDatasource.class)) {
            DynamicSwitchDatasource annotation = targetClass.getAnnotation(DynamicSwitchDatasource.class);
            DatasourceSwitchThreadLocal.set(annotation.value());
            return;
        }

        // 走到这程序没有返回,说明目标方法和目标类上都没有 DynamicSwitchDatasource 注解,使用DS1作为默认的数据源
        DatasourceSwitchThreadLocal.set(DatasourceEnum.DS1);
    }

    /** 在业务方法执行完毕之后执行,无论业务方法是否抛出异常 */
    @After("pointcut()")
    public void afterSwitchDatasource() {
        DatasourceSwitchThreadLocal.remove();
    }
}
