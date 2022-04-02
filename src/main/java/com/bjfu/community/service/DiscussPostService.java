package com.bjfu.community.service;

import com.bjfu.community.dao.DiscussPostMapper;
import com.bjfu.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 帖子相关
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;


    /**
     * 分页查询讨论帖信息
     *
     * @param userId 当传入的 userId = 0 时查找所有用户的帖子
     *               当传入的 userId != 0 时，查找该指定用户的帖子
     * @param offset 每页的起始索引
     * @param limit  每页显示多少条数据
     * @param orderMode  排行模式(若传入 1, 则按照热度来排序)
     * @return
     */
    public List<DiscussPost> findDiscussPosts (int userId, int offset, int limit, int orderMode) {
        /*
        // 查询本地缓存(当查询的是所有用户的帖子并且按照热度排序时)
        if (userId == 0 && orderMode == 1) {
            return postListCache.get(offset + ":" + limit);
        }
        // 查询数据库
        logger.debug("load post list from DB");
        */
        return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
    }

    /**
     * 查询讨论贴的个数
     * @param userId 当传入的 userId = 0 时计算所有用户的帖子总数
     *               当传入的 userId ！= 0 时计算该指定用户的帖子总数
     * @return
     */
    public int findDiscussPostRows (int userId) {
        /*
        // 查询本地缓存(当查询的是所有用户的帖子总数时)
        if (userId == 0) {
            return postRowsCache.get(userId);
        }
        // 查询数据库
        logger.debug("load post rows from DB");
         */
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
