package com.bearcat2.multipledatasource.controller;

import com.bearcat2.multipledatasource.entity.User;
import com.bearcat2.multipledatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> Description: 用户相关的控制器 </p>
 * <p> Title: UserController </p>
 * <p> Create Time: 2019/9/3 16:26 </p>
 *
 * @author zhongzhipeng
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/listUser")
    public List<User> listUser() {
        return this.userService.listUser();
    }

    @PostMapping("/addUser")
    public String addUser(User user) {
        int rows = this.userService.insert(user);
        if (rows > 0) {
            return "success";
        }
        return "error";
    }

    @PostMapping("/addUserUseDs2")
    public String addUserUseDs2(User user) {
        int rows = this.userService.insertDs2(user);
        if (rows > 0) {
            return "success";
        }
        return "error";
    }
}
