package com.lt.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestQueue {
    public static void main(String[] args) throws Exception {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        
        // 创建连接
        Connection connection = connectionFactory.newConnection();
        
        // 创建通道
        Channel channel = connection.createChannel();
        
        // 创建队列
        channel.queueDeclare("TestQueue", false, false, false, null);
        
        // 发送消息
        for (int i = 0; i < 10; i++) {
            String message = "Message " + i;
            channel.basicPublish("", "TestQueue", null, message.getBytes());
        }
        
        // 关闭连接
        channel.close();
        connection.close();
    }
}
