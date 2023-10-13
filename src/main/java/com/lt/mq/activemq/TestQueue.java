package com.lt.mq.activemq;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TestQueue {
    public static void main(String[] args) throws Exception {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        
        // 创建连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        
        // 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        // 创建队列
        Queue queue = session.createQueue("TestQueue");
        
        // 创建生产者
        MessageProducer producer = session.createProducer(queue);
        
        // 发送消息
        for (int i = 0; i < 10; i++) {
            TextMessage message = session.createTextMessage("Message " + i);
            producer.send(message);
        }
        
        // 关闭连接
        connection.close();
    }
}

