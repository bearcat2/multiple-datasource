package com.bearcat2.multipledatasource.service.impl;

import com.bearcat2.multipledatasource.annotation.DynamicSwitchDatasource;
import com.bearcat2.multipledatasource.entity.User;
import com.bearcat2.multipledatasource.enumeration.DatasourceEnum;
import com.bearcat2.multipledatasource.mapper.UserMapper;
import com.bearcat2.multipledatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> Description: 用户 service 接口实现类 </p>
 * <p> Title: UserServiceImpl </p>
 * <p> Create Time: 2019/9/3 16:22 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listUser() {
        return this.userMapper.listUser();
    }

    @Override
    public int insert(User user) {
        return this.userMapper.insert(user);
    }

    @DynamicSwitchDatasource(DatasourceEnum.DS2)
    @Override
    public int insertDs2(User user) {
        return this.userMapper.insert(user);
    }
}
