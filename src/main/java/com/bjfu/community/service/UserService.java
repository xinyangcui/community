package com.bjfu.community.service;

import com.bjfu.community.dao.UserMapper;
import com.bjfu.community.entity.User;
import com.bjfu.community.util.CommunityConstant;
import com.bjfu.community.util.CommunityUtil;
import com.bjfu.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService implements CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    // 网站域名
    @Value("${community.path.domain}")
    private String domain;

    // 项目名(访问路径)
    @Value("${server.servlet.context-path}")
    private String contextPath;


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

    /**
     * 用户注册
     * @param user
     * @return Map<String, Object> 返回错误提示消息，如果返回的 map 为空，则说明注册成功
     */
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }

        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }

        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }


        User u1,u2;
        u1 = userMapper.selectByName(user.getUsername());
        u2 = userMapper.selectByEmail(user.getEmail());

        if(u1!=null&&u2!=null&&u1.getId()==u2.getId()){
            map.put("activationMsg","此账号已存在，但未激活");
            return map;
        }

        // 验证账号是否已存在
        if (u1 != null) {
            map.put("usernameMsg", "该账号已存在");
            return map;
        }

        // 验证邮箱是否已存在
        if (u2 != null) {
            map.put("emailMsg", "该邮箱已被注册");
            return map;
        }



        //注册用户

        user.setSalt(CommunityUtil.generateUUID().substring(0,5));//生成salt
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt())); // 加盐加密
        user.setType(0); // 默认普通用户
        user.setStatus(0); // 默认未激活
        user.setActivationCode(CommunityUtil.generateUUID()); // 激活码
        // 随机头像（用户登录后可以自行修改）
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date()); // 注册时间
        userMapper.insertUser(user);

        // 给注册用户发送激活邮件
        sendActivationMail(user);
        return map ;
    }


    /**
     * 发送用户激活邮件
     * @param user
     * @return
     */
    public void sendActivationMail(User user){
        // 给注册用户发送激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());

        // http://localhost:8080/echo/activation/用户id/激活码
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(),"激活 community 账号", content);

    }

    /**
     * 激活用户
     * @param userId 用户 id
     * @param code 激活码
     * @return
     */
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) {
            // 用户已激活
            return ACTIVATION_REPEAT;
        }
        else if (user.getActivationCode().equals(code)) {
            // 修改用户状态为已激活
            userMapper.updateStatus(userId, 1);
            //clearCache(userId); // 用户信息变更，清除缓存中的旧数据
            return ACTIVATION_SUCCESS;
        }
        else {
            return ACTIVATION_FAILURE;
        }
    }



}
