package com.putaoteng.task8.server;

import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.User;

import java.util.List;

public interface UserDaoServiceRemote {
    public int save(User user);

    public int saveBatch(List<BasicVo> list) ;

    public int update(User user);

    public int updateIgnoreNull(User user);

    public int updateBatch(List<BasicVo> list);

    public int delete(User user);

    public int deleteBatch(List<BasicVo> list);

    public int deleteByPK(Long id);

    public int deleteAll();

    public long count();

    public User findByPK(Long id);

    // public List find(Map<String, Object> paramMap, PageBounds pageBounds);

    public User findByUsername(String username);

    public List<BasicVo> findAll();
}
