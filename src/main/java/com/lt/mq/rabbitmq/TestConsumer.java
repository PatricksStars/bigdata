package com.lt.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class TestConsumer implements Consumer {
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
        
        // 创建消费者
        channel.basicConsume("TestQueue", true, new TestConsumer());
        
        // 等待消息
        Thread.sleep(5000);
        
        // 关闭连接
        channel.close();
        connection.close();
    }
    
    public void handleConsumeOk(String consumerTag) {
        
    }
    
    public void handleCancelOk(String consumerTag) {
        
    }
    
    public void handleCancel(String consumerTag) throws IOException {
        
    }
    
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        
    }
    
    public void handleRecoverOk(String consumerTag) {
        
    }
    
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println("Received message: " + message);
    }
}

