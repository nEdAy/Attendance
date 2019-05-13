package cn.neday.iflytek.controller;

import cn.neday.iflytek.domain.User;
import cn.neday.iflytek.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户列表", notes = "获取所有用户信息")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList() {
        return userService.findAll();
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据UserAccount来获取用户详细信息")
    @RequestMapping(value = "/{UserAccount}", method = RequestMethod.GET)
    public User getUser(@PathVariable String UserAccount) {
        return userService.findByUserAccount(UserAccount);
    }

    @ApiOperation(value = "更新用户Token", notes = "根据UserAccount来指定更新对象,并更新对象的Token信息")
    @RequestMapping(value = "/{UserAccount}/{Token}", method = RequestMethod.PUT)
    public String putUser(@PathVariable String UserAccount, @PathVariable String Token) {
        userService.updateTokenByUserAccount(Token, UserAccount);
        return "success";
    }

}
