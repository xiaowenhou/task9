package com.putaoteng.task8.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.putaoteng.task8.model.BasicVo;

@Repository (value = "basicDao")
public interface BasicDao {
	public int save(BasicVo basicVo);
	
	public int saveBatch(List<BasicVo> list);
	
	public int update(BasicVo basicVo);
	
	public int updateIgnoreNull(BasicVo basicVo);
	
	public int updateBatch(List<BasicVo> list);
	
	public int delete(BasicVo basicVo);
	
	public int deleteBatch(List<BasicVo> list);
	
	public int deleteByPK(Long id);
	
	public int deleteAll();
	
	public long count();
	
	public BasicVo findByPK(Long id);
	
//	public List find(Map<String, Object> paramMap, PageBounds pageBounds);
	
	public List<BasicVo> findAll();
}
