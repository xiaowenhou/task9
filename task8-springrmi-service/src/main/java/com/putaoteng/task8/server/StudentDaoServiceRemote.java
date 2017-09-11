package com.putaoteng.task8.server;

import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.Student;
import org.oasisopen.sca.annotation.Remotable;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

@Remotable
public interface StudentDaoServiceRemote {
     int save(Student student);

     int saveBatch(List<BasicVo> list);

     int update(Student student);

     int updateIgnoreNull(Student student);


     int updateBatch(List<BasicVo> list);

     int delete(Student student);


     int deleteBatch(List<BasicVo> list);

     int deleteByPK(Long id);

     int deleteAll();

     long count();

     Student findByPK(Long id);

     List<BasicVo> findAll();

     List<BasicVo> findByStudentName(String name);
}
