package com.bjfu.community.dao;

import com.bjfu.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
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
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit, int orderMode);


    /**
     * 查询讨论贴的个数
     * @param userId 当传入的 userId = 0 时计算所有用户的帖子总数
     *               当传入的 userId ！= 0 时计算该指定用户的帖子总数
     * @return
     */
    int selectDiscussPostRows(@Param("userId") int userId);
}
