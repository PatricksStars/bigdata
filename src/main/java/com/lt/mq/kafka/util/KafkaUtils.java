package com.lt.mq.kafka.util;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

/**
 * kafka连接
 * @author luot
 * @date   2023年9月15日
 *
 *
 */
public class KafkaUtils {
	
	private static volatile Producer<String, Object> producer;
	
	private static volatile KafkaConsumer<String, Object> consumer;
	
	 /**
     * kafka连接
      */
    private final static String servers = "hadoop129:9092,hadoop130:9092,hadoop131:9092";
	
	/**
	 * 获取生产者
	 * @return
	 */
	public static Producer<String, Object> getProduce(){
		if(producer == null) {
    		synchronized (Producer.class) {
				if(producer == null) {
					// 配置 Kafka 连接信息
			        Properties properties = new Properties();
			        properties.put("bootstrap.servers", servers);
			     // 设置事务ID
			        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "my_tx_id_1");
			        properties.put(ProducerConfig.RETRIES_CONFIG, 1);
			        
			        properties.put("acks", "all");
			        properties.put("batch.size", 16384);
			        properties.put("linger.ms", 1);
			        properties.put("buffer.memory", 33554432);
			        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			        
			        // 创建生产者
			        producer = new KafkaProducer<String, Object>(properties);
				}
			}
    	}
		return producer;
	}
	
	/**
	 * 获取消费者
	 * @return
	 */
	public static KafkaConsumer<String, Object> getConsumer(){
		if(consumer == null) {
    		synchronized (KafkaConsumer.class) {
				if(consumer == null) {
					// 配置 Kafka 连接信息
			        Properties properties = new Properties();
			        properties.put("bootstrap.servers", servers);
			        properties.put("group.id", "TestGroup");
			        properties.put("enable.auto.commit", "false");
			        properties.put("auto.offset.reset", "earliest");
			        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			        
			        // 创建消费者
			        consumer = new KafkaConsumer<String, Object>(properties);
				}
			}
    	}
        return consumer;
	}
	
	/**
	 * 关闭生产者
	 */
	public static void closeProduce() {
		producer.close();
	}
	
	/**
	 * 关闭消费者
	 */
	public static void closeConsumer() {
		consumer.close();
	}
}
