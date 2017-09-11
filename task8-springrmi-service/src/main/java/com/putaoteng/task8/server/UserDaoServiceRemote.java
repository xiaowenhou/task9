package com.putaoteng.task8.server;

import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.User;
import org.oasisopen.sca.annotation.Remotable;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

@Remotable
public interface UserDaoServiceRemote {
     int save(User user);

     int saveBatch(List<BasicVo> list) ;

     int update(User user);

     int updateIgnoreNull(User user);

     int updateBatch(List<BasicVo> list);

     int delete(User user);

     int deleteBatch(List<BasicVo> list);

     int deleteByPK(Long id);

     int deleteAll();

     long count();

     User findByPK(Long id);

    // public List find(Map<String, Object> paramMap, PageBounds pageBounds);

     User findByUsername(String username);

     List<BasicVo> findAll();
}
