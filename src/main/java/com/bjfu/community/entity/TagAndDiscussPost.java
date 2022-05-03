package com.bjfu.community.entity;



public class TagAndDiscussPost {

    private int id;

    private int tagId;

    private int postId;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }


    @Override
    public String toString() {
        return "TagAndDiscussPost{" +
                "id=" + id +
                ", tagId=" + tagId +
                ", postId=" + postId +
                '}';
    }
}
