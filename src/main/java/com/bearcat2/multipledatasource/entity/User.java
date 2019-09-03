package com.bearcat2.multipledatasource.entity;

import lombok.Data;

/**
 * <p> Description: 封装数据库用户表实体类 </p>
 * <p> Title: User </p>
 * <p> Create Time: 2019/9/3 16:11 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
@Data
public class User {

    private Integer id;

    private String name;

    private String password;

    private String gender;

    private Integer age;
}
