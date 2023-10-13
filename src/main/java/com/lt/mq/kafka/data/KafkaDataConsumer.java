//package com.lt.mq.kafka.data;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//
///**
// * kafka消息消费
// * @author luot
// * @date   2023年9月15日
// *
// *
// */
//@Component
//@Slf4j
//public class KafkaDataConsumer {
//    @SuppressWarnings("unused")
//	private final Map<String, Object> strategyMap = new ConcurrentHashMap<String, Object>();
//
////    public KafkaDataConsumer(Map<String, BaseConsumerStrategy> strategyMap){
////        this.strategyMap.clear();
////        strategyMap.forEach((k, v)-> this.strategyMap.put(k, v));
////    }
//    /**
//     * @param record 消息  topics为订阅的队列，可以多个可在配置文件中配置
//     */
//    @KafkaListener(topics = {"testTopic"}, containerFactory = "kafkaListenerContainerFactory")
//    public void receiveMessage(@SuppressWarnings("rawtypes") ConsumerRecord record, Acknowledgment ack) throws Exception {
//        @SuppressWarnings("unused")
//		String message = (String) record.value();
//        //接收消息
////        log.info("Receive from kafka topic[" + record.topic() + "]:" + message);
//
////        try{
////            BaseConsumerStrategy strategy = strategyMap.get(record.topic());
////
////            if(null != strategy){
////                strategy.consumer(message);
////            }
////        }finally {
////            //手动提交保证数据被消费
////            ack.acknowledge();
////        }
//    }
//}
//
