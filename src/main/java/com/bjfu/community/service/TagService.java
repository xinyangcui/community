package com.bjfu.community.service;


import com.bjfu.community.dao.TagAndDiscussPostMapper;
import com.bjfu.community.dao.TagMapper;
import com.bjfu.community.entity.Tag;
import com.bjfu.community.entity.TagAndDiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagAndDiscussPostMapper tapMapper;



    public Tag findTagById(int id){
        return tagMapper.selectById(id);
    }

    public Tag findTagByName(String name){
        return tagMapper.selectByName(name);
    }

    public int findTagCount(){
        return tagMapper.selectCount();
    }


    /**
     * 添加一个标签
     * @param tag
     * @return
     */
    public int addTag(Tag tag){

        if(tag == null){
            throw new IllegalArgumentException("参数不能为空");
        }

        return tagMapper.insertName(tag);
    }

    public List<Tag> findAllTags(int offset, int limit){
        return tagMapper.selectAllTags(offset, limit);
    }

    /**
     * 查询帖子下的tag列表
     * @param postId
     * @return
     */
    public List<Tag> findTagListByPostId(int postId){
        List<Tag> tagList = new ArrayList<>();
        List<TagAndDiscussPost> list = tapMapper.selectByPostId(postId);
        if(list == null){
            return null;
        }

        for(TagAndDiscussPost tap:list){
            int tagId = tap.getTagId();
            Tag tag = tagMapper.selectById(tagId);
            tagList.add(tag);
        }
        return tagList;
    }

    /**
     * 查询一个标签下的所有帖子id列表
     * @param tagId
     * @return
     */
    public List<Integer> findPostListByTagId(int tagId){
        List<Integer> postIdList = new ArrayList<>();
        List<TagAndDiscussPost> list = tapMapper.selectByTagId(tagId);

        if(list == null){
            return null;
        }

        for(TagAndDiscussPost tap:list){
            postIdList.add(tap.getPostId());
        }



        return postIdList;
    }

    public int findPostCountByTagId(int tagId){
       return tapMapper.selectPostCountByTagId(tagId);
    }

    /**
     * 插入一个标签到帖子
     * @return
     */
    public int addTagAndDiscussPost(Tag tag, int postId){
        if(tag == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        TagAndDiscussPost tap = new TagAndDiscussPost();
        tap.setPostId(postId);
        tap.setTagId(tag.getId());

        return tapMapper.insertTagAndDiscussPost(tap);
    }







}
