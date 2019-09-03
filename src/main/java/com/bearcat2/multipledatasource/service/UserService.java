package com.bearcat2.multipledatasource.service;

import com.bearcat2.multipledatasource.entity.User;

import java.util.List;

/**
 * <p> Description: 用户 service 接口  </p>
 * <p> Title: UserService </p>
 * <p> Create Time: 2019/9/3 16:19 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
public interface UserService {

    List<User> listUser();

    int insert(User user);

    int insertDs2(User user);
}
