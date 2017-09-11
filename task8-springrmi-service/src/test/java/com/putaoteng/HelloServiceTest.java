package com.putaoteng;

import com.putaoteng.task8.server.StudentDaoServiceRemote;
import com.putaoteng.task8.server.UserDaoServiceRemote;
import com.putaoteng.task8.server.VerificationDaoServiceRemote;
import org.apache.log4j.Logger;
import org.apache.tuscany.sca.TuscanyRuntime;
import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.junit.Assert;
import org.junit.Test;
import org.oasisopen.sca.NoSuchServiceException;

public class HelloServiceTest {
    private static final Logger logger = Logger.getLogger(HelloServiceTest.class);
    @Test
    public void calculatorServiceTest(){

        Node node = NodeFactory.getInstance().createNode("service-component.composite");
        node.start();
        System.out.println("Server 启动...");
       /* Node node = TuscanyRuntime.runComposite("service-component.composite", "target/classes");
*/
            // Get the Helloworld service proxy
           /* try {
                StudentDaoServiceRemote studentDaoServiceRemote = node.getService(StudentDaoServiceRemote.class, "ServiceComponent");
                long result = studentDaoServiceRemote.count();
                logger.error("--------------------------------"+result);
            } catch (NoSuchServiceException e) {
                e.printStackTrace();
            }finally {
                // Stop the Tuscany runtime Node
                node.stop();*/

        }
        /* // Run the SCA composite in a Tuscany runtime
        Node node = NodeFactory.getInstance().createNode("helloService.composite");

      //  StudentDaoServiceRemote studentDaoServiceRemote = node.getService(StudentDaoServiceRemote.class, "HelloworldComponent");
        node.start();
        System.out.println("service启动");

        StudentDaoServiceRemote studentDaoServiceRemote = node.getService(StudentDaoService.class, "HelloworldComponent");
        System.out.println(studentDaoServiceRemote.count());*/


      /* Node node = TuscanyRuntime.runComposite("service-component.composite", "target/classes");
        try {

            // Get the Helloworld service proxy

            // test that it works as expected
            //Assert.assertEquals("Hello Amelia", helloworld.sayHello("Amelia"));
            StudentDaoServiceRemote studentDaoServiceRemote = node.getService(StudentDaoServiceRemote.class, "ServiceComponent");
            long result = studentDaoServiceRemote.count();
            logger.error("------------------------------------------------------------------Student:" + result);

           *//* UserDaoServiceRemote userDaoServiceRemote = node.getService(UserDaoServiceRemote.class, "ServiceComponent");
            result = studentDaoServiceRemote.count();
            logger.error("------------------------------------------------------------------User:" + result);

            VerificationDaoServiceRemote verificationDaoServiceRemote = node.getService(VerificationDaoServiceRemote.class, "ServiceComponent");
            result = studentDaoServiceRemote.count();
            logger.error("------------------------------------------------------------------Verification" + result);
*//*
        } catch (NoSuchServiceException e) {
            e.printStackTrace();
        } finally {
            // Stop the Tuscany runtime Node
            node.stop();
        }*/

}
