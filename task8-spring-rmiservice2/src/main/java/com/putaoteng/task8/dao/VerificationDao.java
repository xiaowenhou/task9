package com.putaoteng.task8.dao;

import com.putaoteng.task8.model.Verification;
import org.springframework.stereotype.Repository;

@Repository (value = "verificationDao")
public interface VerificationDao extends BasicDao{
	public Verification findByPhoneNumber(String phoneNumber);
	
	public Verification findByEmail(String email);
	
	public Verification findByCreateAt(long createTime);
}
