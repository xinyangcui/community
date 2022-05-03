package com.bjfu.community.dao;


import com.bjfu.community.entity.TagAndDiscussPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagAndDiscussPostMapper {

    /**
     * 根据postid查询所属标签id
     * @param postId
     * @return
     */
    List<TagAndDiscussPost> selectByPostId(int postId);


    /**
     * 根据标签id来查找拥有该标签的帖子id
     * @param tagId
     * @return
     */
    List<TagAndDiscussPost> selectByTagId(int tagId);


    /**
     * 查询标签id下对应的帖子数量
     * @param tagId
     * @return
     */
    int selectPostCountByTagId(int tagId);


    /**
     * 插入标签到帖子
     * @param tap
     * @return
     */
    int insertTagAndDiscussPost(TagAndDiscussPost tap);

}
