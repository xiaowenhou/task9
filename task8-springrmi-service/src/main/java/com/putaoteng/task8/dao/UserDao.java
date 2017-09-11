package com.putaoteng.task8.dao;


import org.springframework.stereotype.Repository;

import com.putaoteng.task8.model.User;

@Repository (value="userDao")
public interface UserDao extends BasicDao {
	User findByUserName(String username);
}
