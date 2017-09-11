package com.putaoteng.task8.server;

import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.Student;

import java.util.List;

public interface StudentDaoServiceRemote {
    public int save(Student student);

    public int saveBatch(List<BasicVo> list);

    public int update(Student student);

    public int updateIgnoreNull(Student student);


    public int updateBatch(List<BasicVo> list);

    public int delete(Student student);


    public int deleteBatch(List<BasicVo> list);

    public int deleteByPK(Long id);

    public int deleteAll();

    public long count();

    public Student findByPK(Long id);

    public List<BasicVo> findAll();

    public List<BasicVo> findByStudentName(String name);
}
