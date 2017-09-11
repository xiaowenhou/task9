package com.putaoteng.task8.dao;


import com.putaoteng.task8.model.User;
import org.springframework.stereotype.Repository;

@Repository (value="userDao")
public interface UserDao extends BasicDao {
	User findByUserName(String username);
}
