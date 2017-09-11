package com.putaoteng.task8.service;

import com.putaoteng.task8.dao.VerificationDao;
import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.Verification;
import com.putaoteng.task8.server.VerificationDaoServiceRemote;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service (value="verificationDaoService")
public class VerificationDaoService implements VerificationDaoServiceRemote, Serializable {
	private static final long serialVersionUID = 5183585298519824378L;

	@Resource(name = "verificationDao")
	private VerificationDao verificationDao;

	public int save(Verification verification) {
		return verificationDao.save(verification);
	}

	public int saveBatch(List<BasicVo> list) {
		return verificationDao.saveBatch(list);
	}

	public int update(Verification verification) {
		return verificationDao.update(verification);
	}

	public int updateIgnoreNull(Verification verification) {
		return verificationDao.updateIgnoreNull(verification);
	}

	public int updateBatch(List<BasicVo> list) {
		return verificationDao.updateBatch(list);
	}

	public int delete(Verification verification) {
		return  verificationDao.delete(verification);
	}

	public int deleteBatch(List<BasicVo> list) {
		return verificationDao.deleteBatch(list);
	}

	public int deleteByPK(Long id) {
		return verificationDao.deleteByPK(id);
	}

	public int deleteAll() {
		return verificationDao.deleteAll();
	}

	public long count() {
		return verificationDao.count();
	}

	public Verification findByPK(Long id) {
		return (Verification) verificationDao.findByPK(id);
	}

	// public List find(Map<String, Object> paramMap, PageBounds pageBounds);

	public Verification findByPhoneNumber(String phoneNumber) {
		return verificationDao.findByPhoneNumber(phoneNumber);
	}
	
	public Verification findByEmail(String email) {
		return verificationDao.findByEmail(email);
	}

	public Verification findByCreateAt(long createAt) {
		return verificationDao.findByCreateAt(createAt);
	}
	
	public List<BasicVo> findAll() {
			return verificationDao.findAll();
	}
}
