package com.bearcat2.multipledatasource.mapper;

import com.bearcat2.multipledatasource.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p> Description: 操作用户的mapper对象 </p>
 * <p> Title: UserMapper </p>
 * <p> Create Time: 2019/9/3 16:13 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
public interface UserMapper {

    @Select("SELECT * FROM `user`")
    List<User> listUser();

    @Insert("INSERT INTO `user`(`name`,`password`,gender,age) VALUES (#{name},#{password},#{gender},#{age})")
    int insert(User user);
}
