package com.bjfu.community.dao;


import com.bjfu.community.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {


    /**
     * 根据Tagid查询标签
     * @param id
     * @return
     */
    Tag selectById(int id);

    /**
     *
     * @param name
     * @return
     */
    Tag selectByName(String name);

    /**
     * 查询所有标签数量
     * @return
     */
    int selectCount();

    /**
     * 查询所有标签
     * @param offset
     * @param limit
     * @return
     */
    List<Tag> selectAllTags(int offset, int limit);

    /**
     *
     * @param tag
     * @return
     */
    int insertName(Tag tag);

}
