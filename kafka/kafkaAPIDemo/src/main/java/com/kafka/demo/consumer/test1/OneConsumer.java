package com.kafka.demo.consumer.test1;

import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class OneConsumer extends ShutdownableThread {

    private KafkaConsumer<Integer,String> consumer;

    public OneConsumer() {
        super("kafkaOneConsumerTest", false);
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"kafka1:9092");
        // 指定消费者组ID
        properties.put("group.id", "cityGroup1");
        // 开启自动提交，默认为true
        properties.put("enable.auto.commit", "true");
        // 指定自动提交的超时时限，默认5s
        properties.put("auto.commit.interval.ms", "1000");
        // 指定消费者被broker认定为挂掉的时限。若broker在此时间内未收到当前消费者发送的心跳，则broker
        // 认为消费者已经挂掉。默认为10s
        properties.put("session.timeout.ms", "30000");
        // 指定两次心跳的时间间隔，默认为3s，一般不要超过session.timeout.ms的 1/3
        properties.put("heartbeat.interval.ms", "10000");
        // 当kafka中没有指定offset初值时，或指定的offset不存在时，从这里读取offset的值。其取值的意义为：
        // earliest:指定offset为第一条offset
        // latest: 指定offset为最后一条offset
        properties.put("auto.offset.reset", "earliest");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<Integer, String>(properties);
    }

    @Override
    public void doWork() {
        this.consumer.subscribe(Collections.singletonList("city111"));
        ConsumerRecords<Integer, String> poll = this.consumer.poll(Duration.ofSeconds(1000));
        for (ConsumerRecord consumerRecord : poll){
            System.out.println(consumerRecord.topic());
        }
    }


}
