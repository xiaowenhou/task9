package com.putaoteng.task8.server;

import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.Verification;
import org.oasisopen.sca.annotation.Remotable;

import java.util.List;

@Remotable
public interface VerificationDaoServiceRemote {
     int save(Verification verification);

     int saveBatch(List<BasicVo> list);

     int update(Verification verification);

     int updateIgnoreNull(Verification verification);

     int updateBatch(List<BasicVo> list);

     int delete(Verification verification);

     int deleteBatch(List<BasicVo> list);

     int deleteByPK(Long id);

     int deleteAll();

     long count();

     Verification findByPK(Long id) ;

    // public List find(Map<String, Object> paramMap, PageBounds pageBounds);

     Verification findByPhoneNumber(String phoneNumber);


     Verification findByEmail(String email);

     Verification findByCreateAt(long createAt);

     List<BasicVo> findAll();
}
