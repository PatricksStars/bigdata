package com.lt.mq.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import com.lt.mq.kafka.util.KafkaUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestConsumer {
    public static void main(String[] args) throws Exception {
        KafkaConsumer<String, Object> consumer = KafkaUtils.getConsumer();
        consumer.subscribe(Arrays.asList("TestTopic"));
        
        CountDownLatch count = new CountDownLatch(1);
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					while(0 < count.getCount()) {
						// 消费消息
						@SuppressWarnings("deprecation")
						ConsumerRecords<String, Object> records = consumer.poll(100);
						
						for (ConsumerRecord<String, Object> record : records) {
							System.out.println("Received message: " + record.value());
							
							// 手动提交消费位移
							Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<TopicPartition, OffsetAndMetadata>();
							offsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
							consumer.commitSync(offsets);
						}
					}
				}finally {
					consumer.close();
					count.countDown();
				}
	       
				
			}
		}).start();
 
        count.await(10,TimeUnit.SECONDS);
        count.countDown();
    }
}
