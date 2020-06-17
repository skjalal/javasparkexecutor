package com.example;

import kafka.EmbeddedSingleNodeKafkaCluster;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Properties;

@Slf4j
@ExtendWith(SpringExtension.class)
class JavaSparkExecutorTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster embeddedSingleNodeKafkaCluster = new EmbeddedSingleNodeKafkaCluster();

    private static final String TOPIC_NAME = "TestTopic";

    @Test
    void testMain() {
        embeddedSingleNodeKafkaCluster.createTopic(TOPIC_NAME);
        log.info("Topic Created...!");
        produceSampleRecords();
        JavaSparkExecutor.main(new String[]{});
        Assertions.assertTrue(true);
    }

    private void produceSampleRecords() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedSingleNodeKafkaCluster.bootstrapServer());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put("schema.registry.url", embeddedSingleNodeKafkaCluster.schemaRegistryUrl());
        KafkaProducer<String, GenericRecord> producer = new KafkaProducer<>(props);

        String employeeSchema = "{\"type\":\"record\"," +
                "\"name\":\"Employee\"," +
                "\"fields\":[{\"name\":\"employeeId\",\"type\":\"int\"}, {\"name\":\"employeeName\",\"type\":\"string\"}, {\"name\":\"designation\",\"type\":\"string\"}]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(employeeSchema);
        GenericRecord record = new GenericData.Record(schema);
        record.put("employeeId", 101);
        record.put("employeeName", "EMP101");
        record.put("designation", "SE");
        ProducerRecord<String, GenericRecord> producerRecord = new ProducerRecord<>(TOPIC_NAME, "KEY101", record);
        producer.send(producerRecord, (metadata, exception) -> log.info("Produced Employee Data {}", metadata));
        producer.close();
    }
}