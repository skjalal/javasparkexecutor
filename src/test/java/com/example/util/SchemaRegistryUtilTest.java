package com.example.util;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import kafka.EmbeddedSingleNodeKafkaCluster;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void testGetStructType() throws IOException, RestClientException {
        cluster.produceSampleRecords(TOPIC_NAME);
        SchemaRegistryUtil schemaRegistryUtil = SchemaRegistryUtil.getInstance(cluster.schemaRegistryUrl());
        assertNotNull(schemaRegistryUtil);
        CachedSchemaRegistryClient cachedSchemaRegistryClient = schemaRegistryUtil.getCachedSchemaRegistryClient();
        assertNotNull(cachedSchemaRegistryClient);
        StructType structType = schemaRegistryUtil.getStructType(TOPIC_NAME);
        assertNotNull(structType);
    }

    @Test
    void testExceptionGetStructType() {
        SchemaRegistryUtil schemaRegistryUtil = SchemaRegistryUtil.getDefaultInstance();
        assertThrows(Exception.class, () -> schemaRegistryUtil.getStructType(null));
    }
}