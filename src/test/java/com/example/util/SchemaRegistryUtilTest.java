package com.example.util;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import kafka.EmbeddedSingleNodeKafkaCluster;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class SchemaRegistryUtilTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster cluster = new EmbeddedSingleNodeKafkaCluster();

    private static final String TOPIC_NAME = "TestTopic";

    @Test
    void testGetDefaultInstance() {
        SchemaRegistryUtil schemaRegistryUtil = SchemaRegistryUtil.getDefaultInstance();
        assertNotNull(schemaRegistryUtil);
    }

    @Test
    void testGetInstance() {
        SchemaRegistryUtil schemaRegistryUtil = SchemaRegistryUtil.getInstance(cluster.schemaRegistryUrl());
        assertNotNull(schemaRegistryUtil);
    }

    @Test
    void testGetCachedSchemaRegistryClient() {
        SchemaRegistryUtil schemaRegistryUtil = SchemaRegistryUtil.getInstance(cluster.schemaRegistryUrl());
        assertNotNull(schemaRegistryUtil);
        CachedSchemaRegistryClient cachedSchemaRegistryClient = schemaRegistryUtil.getCachedSchemaRegistryClient();
        assertNotNull(cachedSchemaRegistryClient);
    }

    @Test
    void testGetStructType() {
        produceSampleRecords();
        SchemaRegistryUtil schemaRegistryUtil = SchemaRegistryUtil.getInstance(cluster.schemaRegistryUrl());
        assertNotNull(schemaRegistryUtil);
        CachedSchemaRegistryClient cachedSchemaRegistryClient = schemaRegistryUtil.getCachedSchemaRegistryClient();
        assertNotNull(cachedSchemaRegistryClient);
        StructType structType = schemaRegistryUtil.getStructType(TOPIC_NAME);
        assertNotNull(structType);
    }

    private void produceSampleRecords() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, cluster.bootstrapServer());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put("schema.registry.url", cluster.schemaRegistryUrl());
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