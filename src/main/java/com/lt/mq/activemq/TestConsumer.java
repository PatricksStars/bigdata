package com.lt.mq.activemq;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TestConsumer implements MessageListener {
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
        
        // 创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new TestConsumer());
        
        // 等待消息
        Thread.sleep(5000);
        
        // 关闭连接
        connection.close();
    }
    
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Received message: " + textMessage.getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

