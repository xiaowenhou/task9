package com.putaoteng.task8.service;

import com.putaoteng.task8.dao.StudentDao;
import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.Student;
import com.putaoteng.task8.server.StudentDaoServiceRemote;
import com.putaoteng.task8.utils.cache.MemcachedCache;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service(value = "studentDaoService")
public class StudentDaoService  implements StudentDaoServiceRemote{
   /* private static final long serialVersionUID = -561489057780686738L;*/

    @Resource(name = "studentDao")
    private StudentDao studentDao;
    @Resource(name = "memcachedCache")
    private MemcachedCache memcachedCache;

    @Override
    public int save(Student student) {
        int result = studentDao.save(student);

        if (result != 0) {
            if (memcachedCache.get("studentList") != null) {
                List<BasicVo> list = studentDao.findAll();
                memcachedCache.update("studentList", list);
            }
            if (memcachedCache.get("studentName" + student.getName()) != null) {

                List<BasicVo> listName = studentDao.findByStudentName(student.getName());
                memcachedCache.update("studentName" + student.getName(), listName);
            }
        }
        return result;
    }

    @Override
    public int saveBatch(List<BasicVo> list) {
        return studentDao.saveBatch(list);
    }

    @Override
    public int update(Student student) {
        // 先找到更新前的相应数据
        long id = student.getId();
        Student perStudent = (Student) studentDao.findByPK(id);
        // 更新数据库中的数据
        int result = studentDao.update(student);
        if (result != 0) {
            if (memcachedCache.get("studentList") != null) {
                // 更新缓存中的List key
                List<BasicVo> list = studentDao.findAll();
                memcachedCache.update("studentList", list);
            }
            // 更新缓存中的id key
            if (memcachedCache.get("student" + student.getId()) != null) {
                memcachedCache.update("student" + student.getId(), student);
            }
            // 更新缓存中的Name key
            if (memcachedCache.get("studentName" + perStudent.getName()) != null) {
                memcachedCache.delete("studentName" + perStudent.getName());
                List<BasicVo> listName = studentDao.findByStudentName(student.getName());
                memcachedCache.set("studentName" + student.getName(), 0, listName);
            }
        }
        return result;
    }

    @Override
    public int updateIgnoreNull(Student student) {
        long id = student.getId();
        Student perStudent = (Student) studentDao.findByPK(id);

        int result = studentDao.updateIgnoreNull(student);

        if (result != 0) {
            if (memcachedCache.get("studentList") != null) {
                // 更新缓存中的List key
                List<BasicVo> list = studentDao.findAll();
                memcachedCache.update("studentList", list);
            }

            // 更新缓存中的id key
            if (memcachedCache.get("student" + student.getId()) != null) {
                memcachedCache.update("student" + student.getId(), student);
            }

            // 更新缓存中的Name key
            if (memcachedCache.get("studentName" + perStudent.getName()) != null) {
                memcachedCache.delete("studentName" + perStudent.getName());
                List<BasicVo> listName = studentDao.findByStudentName(student.getName());
                memcachedCache.set("studentName" + student.getName(), 0, listName);
            }
        }
        return result;
    }

    @Override
    public int updateBatch(List<BasicVo> list) {
        int result = studentDao.updateBatch(list);
        if (result != 0) {
            if (memcachedCache.get("studentList") != null) {
                // 更新缓存中的List key
                List<BasicVo> listAll = studentDao.findAll();
                memcachedCache.update("studentList", listAll);
            }

            // 更新缓存中的id key
            for (BasicVo basicVo: list) {
                Student student = (Student)basicVo;
                if (memcachedCache.get("student" + student.getId()) != null) {
                    memcachedCache.update("student" + student.getId(), student);
                }
            }
            //更新缓存中的name key,暂时还没想到...
        }
        return result;
    }

    @Override
    public int delete(Student student) {
        //删除相应ID缓存
        memcachedCache.delete("student" + student.getId());

        int result = studentDao.delete(student);

        if (result != 0) {
            if (memcachedCache.get("studentList") != null) {
                List<BasicVo> list = studentDao.findAll();
                memcachedCache.update("studentList", list);
            }

            if (memcachedCache.get("studentName" + student.getName()) != null) {
                List<BasicVo> listName = studentDao.findByStudentName(student.getName());
                memcachedCache.update("studentName" + student.getName(), listName);
            }
        }
        return result;
    }

    @Override
    public int deleteBatch(List<BasicVo> list) {
        return studentDao.deleteBatch(list);
    }

    @Override
    public int deleteByPK(Long id) {
        memcachedCache.delete("student" + id);
        Student student = (Student) studentDao.findByPK(id);

        int result = studentDao.deleteByPK(id);

        if (result != 0) {
            if (memcachedCache.get("studentList") != null) {
                List<BasicVo> list = studentDao.findAll();
                memcachedCache.update("studentList", list);
            }

            if (memcachedCache.get("studentName" + student.getName()) != null) {
                List<BasicVo> listName = studentDao.findByStudentName(student.getName());
                memcachedCache.update("studentName" + student.getName(), listName);
            }
        }
        return result;
    }

    @Override
    public int deleteAll() {
        return studentDao.deleteAll();
    }

    @Override
    public long count() {
        return studentDao.count();
    }

    @Override
    public Student findByPK(Long id) {
        Student student = (Student) memcachedCache.get("student" + id);

        if (student == null) {
            student = (Student) studentDao.findByPK(id);
            if (student == null) {
                memcachedCache.set("student" + id, 5 * 60, "");
            } else {
                memcachedCache.set("student" + id, 0, student);
            }
        }
        return student;
    }

    // public List find(Map<String, Object> paramMap, PageBounds pageBounds);

    @Override
    public List<BasicVo> findAll() {
        @SuppressWarnings("unchecked")
        List<BasicVo> list = (List<BasicVo>) memcachedCache.get("studentList");

        if (list == null) {
            list = studentDao.findAll();
            if (list.isEmpty()) {
                memcachedCache.set("studentList", 5 * 60, list);
            } else {
                memcachedCache.set("studentList", 0, list);
            }
        }
        return list;
    }

    @Override
    public List<BasicVo> findByStudentName(String name) {
        @SuppressWarnings("unchecked")
        List<BasicVo> list = (List<BasicVo>) memcachedCache.get("studentName" + name);

        if (list == null) {
            list = studentDao.findByStudentName(name);
            if (list.isEmpty()) {
                memcachedCache.set("studentName" + name, 5 * 60, list);
            } else {
                memcachedCache.set("studentName" + name, 0, list);
            }
        }
        return list;
    }
}
