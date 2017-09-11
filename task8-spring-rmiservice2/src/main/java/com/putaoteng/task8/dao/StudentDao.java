package com.putaoteng.task8.dao;

import com.putaoteng.task8.model.BasicVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository (value="studentDao")
public interface StudentDao extends BasicDao {
	public List<BasicVo> findByStudentName(String name);
}
