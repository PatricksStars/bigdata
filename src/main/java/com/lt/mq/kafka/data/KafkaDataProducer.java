//package com.lt.mq.kafka.data;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.producer.RecordMetadata;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
///**
// * 向kafka推送数据
// * @author luot
// * @date   2023年9月15日
// *
// *
// */
//@Slf4j
//@Component
//public class KafkaDataProducer {
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    /**
//     * 向kafka push表数据，同步到本地
//     *
//     * @param msg   消息
//     * @param topic 主题
//     * @throws Exception 异常
//     */
//    public RecordMetadata sendMsg(String msg, String topic) throws Exception {
//        try {
//            String defaultTopic = kafkaTemplate.getDefaultTopic();
//            System.out.println("defaultTopic:"+defaultTopic);
//            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, msg);
//            return future.get().getRecordMetadata();
//        } catch (Exception e) {
////            log.error("sendMsg to kafka failed!", e);
//            throw e;
//        }
//    }
//}
