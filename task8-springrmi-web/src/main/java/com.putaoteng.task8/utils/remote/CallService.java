package com.putaoteng.task8.utils.remote;

import com.putaoteng.task8.server.StudentDaoServiceRemote;
import com.putaoteng.task8.server.UserDaoServiceRemote;
import com.putaoteng.task8.server.VerificationDaoServiceRemote;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;

public class CallService {
    private UserDaoServiceRemote userDaoServiceRemote;
    private StudentDaoServiceRemote studentDaoServiceRemote;

    public UserDaoServiceRemote getUserDaoServiceRemote() {
        return userDaoServiceRemote;
    }

    public void setUserDaoServiceRemote(UserDaoServiceRemote userDaoServiceRemote) {
        this.userDaoServiceRemote = userDaoServiceRemote;
    }

    public StudentDaoServiceRemote getStudentDaoServiceRemote() {
        return studentDaoServiceRemote;
    }

    public void setStudentDaoServiceRemote(StudentDaoServiceRemote studentDaoServiceRemote) {
        this.studentDaoServiceRemote = studentDaoServiceRemote;
    }

    public CallService(){
        ApplicationContext context = registratContext();
        this.userDaoServiceRemote =
                (UserDaoServiceRemote) context.getBean("rmiProxyUser");
        this.studentDaoServiceRemote =
                (StudentDaoServiceRemote) context.getBean("rmiProxyStudent");
    }

    private ApplicationContext registratContext(){
        Random random = new Random();
        ApplicationContext context;
        //捕捉BeanCreationException,如果发现该异常,说明该服务被中断,转向另一个服务
        if (random.nextInt(10) % 2 == 0) {
            try {
                context = new ClassPathXmlApplicationContext("remote-context-1.xml");
            } catch (BeanCreationException e) {
                context = new ClassPathXmlApplicationContext("remote-context-2.xml");
            }
        } else {
            try {
                context = new ClassPathXmlApplicationContext("remote-context-2.xml");
            } catch (BeanCreationException e) {
                context = new ClassPathXmlApplicationContext("remote-context-1.xml");
            }
        }
        return context;
    }

    public void getBeanAgain(){
        ApplicationContext context = registratContext();
        this.userDaoServiceRemote =
                (UserDaoServiceRemote) context.getBean("rmiProxyUser");
        this.studentDaoServiceRemote =
                (StudentDaoServiceRemote) context.getBean("rmiProxyStudent");
    }
}
