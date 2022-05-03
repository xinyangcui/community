package com.bjfu.community;

import com.bjfu.community.dao.*;
import com.bjfu.community.entity.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagAndDiscussPostMapper tagAndDiscussPostMapper;



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

    @Test
    public void testInsertTicket(){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setStatus(0);
        loginTicket.setTicket("abc");
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000*60*10));

        loginTicketMapper.insertLoginTicket(loginTicket);

    }
    @Test
    public void testSelectLoginTicket(){
        LoginTicket loginTicket=loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abc",1);
        loginTicket=loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

    }

    @Test
    public void testSelect(){
        TagAndDiscussPost tap = new TagAndDiscussPost();
        tap.setTagId(2);
        tap.setPostId(5);
        int rows = tagAndDiscussPostMapper.insertTagAndDiscussPost(tap);

        List<TagAndDiscussPost> list = tagAndDiscussPostMapper.selectByTagId(2);
        for(TagAndDiscussPost tagAndDiscussPost:list){
            System.out.println(tagAndDiscussPost.getPostId());
        }

        list = tagAndDiscussPostMapper.selectByPostId(1);
        for(TagAndDiscussPost tagAndDiscussPost:list){
            System.out.println(tagAndDiscussPost.getTagId());
        }





    }



}
