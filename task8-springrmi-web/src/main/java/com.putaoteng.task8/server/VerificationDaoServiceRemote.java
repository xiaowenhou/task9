package com.putaoteng.task8.server;

import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.Verification;

import java.util.List;

public interface VerificationDaoServiceRemote {
    public int save(Verification verification);

    public int saveBatch(List<BasicVo> list);

    public int update(Verification verification);

    public int updateIgnoreNull(Verification verification);

    public int updateBatch(List<BasicVo> list);

    public int delete(Verification verification);

    public int deleteBatch(List<BasicVo> list);

    public int deleteByPK(Long id);

    public int deleteAll();

    public long count();

    public Verification findByPK(Long id) ;

    // public List find(Map<String, Object> paramMap, PageBounds pageBounds);

    public Verification findByPhoneNumber(String phoneNumber);


    public Verification findByEmail(String email);

    public Verification findByCreateAt(long createAt);

    public List<BasicVo> findAll();
}
