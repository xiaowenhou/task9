package com.putaoteng.task8.controller;

import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;

public class StartService {
    public static void main(String[] args){
      /* System.setProperty("java.rmi.server.hostname","120.77.169.243");*/
        Node node = NodeFactory.getInstance().createNode("service-component.composite");
        node.start();
        System.out.println("Server 启动...");
    }
}
