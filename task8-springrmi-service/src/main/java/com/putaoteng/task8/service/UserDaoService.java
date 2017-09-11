package com.putaoteng.task8.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.putaoteng.task8.server.UserDaoServiceRemote;
import org.springframework.stereotype.Service;

import com.putaoteng.task8.dao.UserDao;
import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.User;

@Service(value = "userDaoService")
public class UserDaoService implements UserDaoServiceRemote, Serializable {
	private static final long serialVersionUID = 3826689364303634320L;

	@Resource(name = "userDao")
	private UserDao userDao;

	@Override
	public int save(User user) {
		return userDao.save(user);
	}

	@Override
	public int saveBatch(List<BasicVo> list) {
		return userDao.saveBatch(list);
	}

	@Override
	public int update(User user) {
		return userDao.update(user);
	}

	@Override
	public int updateIgnoreNull(User user) {
		return userDao.updateIgnoreNull(user);
	}

	@Override
	public int updateBatch(List<BasicVo> list) {
		return userDao.updateBatch(list);
	}

	@Override
	public int delete(User user) {
		return  userDao.delete(user);
	}

	@Override
	public int deleteBatch(List<BasicVo> list) {
		return userDao.deleteBatch(list);
	}

	@Override
	public int deleteByPK(Long id) {
		return userDao.deleteByPK(id);
	}

	@Override
	public int deleteAll() {
		return userDao.deleteAll();
	}

	@Override
	public long count() {
		return userDao.count();
	}

	@Override
	public User findByPK(Long id) {
		return (User) userDao.findByPK(id);
	}

	// public List find(Map<String, Object> paramMap, PageBounds pageBounds);

	@Override
	public User findByUsername(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public List<BasicVo> findAll() {
			return userDao.findAll();
	}
}
