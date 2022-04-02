package com.bjfu.community.dao;

import org.springframework.stereotype.Repository;

@Repository("alphaDaoHibernate") //dao层使用
public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
