package com.kafka.demo.producer.test2;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class TwoProducer {

    private KafkaProducer<Integer,String> producer;

    public TwoProducer() {
        Properties properties = new Properties();
        properties.setProperty(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,"kafka1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<Integer, String>(properties);
    }

    public void sendMsg(){
        ProducerRecord<Integer, String> record = new ProducerRecord("city2",0,1,"dalian");
        this.producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                System.out.println(metadata.partition());
                System.out.println(metadata.topic());
                System.out.println(metadata.offset());
            }
        });
    }
}
