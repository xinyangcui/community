package com.bjfu.community.service;

import com.bjfu.community.dao.UserMapper;
import com.bjfu.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 根据 Id 查询用户
     * @param id
     * @return
     */
    public User findUserById (int id) {
        return userMapper.selectById(id);
        /*
        User user = getCache(id); // 优先从缓存中查询数据
        if (user == null) {
            user = initCache(id);
        }
        return user;
         */
    }

    /**
     * 根据 username 查询用户
     * @param username
     * @return
     */
    public User findUserByName(String username) {
        return userMapper.selectByName(username);
    }
}
