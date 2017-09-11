package com.putaoteng.task8.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.putaoteng.task8.model.BasicVo;

@Repository (value="studentDao")
public interface StudentDao extends BasicDao {
	public List<BasicVo> findByStudentName(String name);
}
