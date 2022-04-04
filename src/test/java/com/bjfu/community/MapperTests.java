package com.bjfu.community;

import com.bjfu.community.dao.DiscussPostMapper;
import com.bjfu.community.dao.UserMapper;
import com.bjfu.community.entity.DiscussPost;
import com.bjfu.community.entity.User;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


import java.util.Date;
import java.util.List;


@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;


    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("SYSTEM");
        System.out.println(user);

        User user1 = userMapper.selectByEmail("nowcoder1@sina.com");
        System.out.println(user1);
        System.out.println(user.equals(user1));
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        int row = userMapper.insertUser(user);
        System.out.println(row);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdateUser(){
        int row = userMapper.updateStatus(150,1);
        System.out.println(row);

        row = userMapper.updateHeader(150,"http://www.nowcoder.com/102.png");
        System.out.println(row);



    }

    @Test
    public void testSelectDiscussPost(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(149,0,10,0);

       for(DiscussPost discussPost:list){
           System.out.println(discussPost);
       }

       int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);

    }


}
