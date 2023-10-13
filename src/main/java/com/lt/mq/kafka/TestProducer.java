package com.lt.mq.kafka;

import org.apache.kafka.clients.producer.*;
import com.lt.mq.kafka.util.KafkaUtils;

public class TestProducer {
    public static void main(String[] args) throws Exception {
        
        // 创建生产者
        Producer<String, Object> producer = KafkaUtils.getProduce();
        
        // 发送消息
        for (int i = 0; i < 10; i++) {
            String message = "Message " + i;
            producer.send(new ProducerRecord<String, Object>("TestTopic", message));
        }
        
        // 关闭生产者
        producer.close();
    }
}

