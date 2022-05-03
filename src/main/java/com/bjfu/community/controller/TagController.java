package com.bjfu.community.controller;


import com.bjfu.community.entity.DiscussPost;
import com.bjfu.community.entity.Page;
import com.bjfu.community.entity.Tag;
import com.bjfu.community.entity.User;
import com.bjfu.community.service.DiscussPostService;
import com.bjfu.community.service.LikeService;
import com.bjfu.community.service.TagService;
import com.bjfu.community.service.UserService;
import com.bjfu.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class TagController implements CommunityConstant {

    @Autowired
    private TagService tagService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;



    /**
     * 标签列表
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/tag/list")
    public String getTagList(Model model, Page page){

        // 分页信息
        page.setLimit(20);
        page.setPath("/tag/list");
        page.setRows(tagService.findTagCount());
        List<Tag> tagList = tagService.findAllTags(page.getOffset(), page.getLimit());
        System.out.println(tagList.size());

        List<Map<String,Object>> tags = new ArrayList<>();
        if(tagList != null){
            Collections.sort(tagList, new Comparator<Tag>() {
                @Override
                public int compare(Tag o1, Tag o2) {
                    int count1 = tagService.findPostCountByTagId(o1.getId());
                    int count2 = tagService.findPostCountByTagId(o2.getId());
                    int diff = count1 -count2;
                    if(diff> 0){
                        return -1;
                    }
                    else if(diff < 0){
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            });

            for(Tag tag:tagList){
                Map<String, Object> map = new HashMap<>();
                map.put("tag",tag);
                map.put("postCount",tagService.findPostCountByTagId(tag.getId()));

                tags.add(map);

            }

        }
        model.addAttribute("tags",tags);


        return "/site/tag";




    }


    /**
     * 标签详情页
     * @param tagId
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/tag/detail/{tagId}")
    public String getTagDetail(@PathVariable("tagId") int tagId,Model model,Page page){

        //分页信息
        page.setLimit(5);
        page.setPath("/tag/detail/" + tagId);
        page.setRows(tagService.findPostCountByTagId(tagId));

        List<Integer> postIdList = tagService.findPostListByTagId(tagId);
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(postIdList != null){
            for(Integer postId:postIdList){
                Map<String, Object> map = new HashMap<>();

                DiscussPost post = discussPostService.findDiscussPostById(postId);
                map.put("post",post);
                // 作者
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);

                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
                map.put("likeCount", likeCount);

                List<Tag> tagsList = tagService.findTagListByPostId(post.getId());
                map.put("tagsList",tagsList);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);


        return "/site/tag-detail";




    }


}
