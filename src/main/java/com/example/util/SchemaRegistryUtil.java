package com.example.util;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.spark.sql.avro.SchemaConverters;
import org.apache.spark.sql.types.StructType;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class SchemaRegistryUtil {

    private static final SchemaRegistryUtil SCHEMA_REGISTRY_UTIL = new SchemaRegistryUtil();
    private static CachedSchemaRegistryClient cachedSchemaRegistryClient;

    private SchemaRegistryUtil() {}

    private SchemaRegistryUtil(final String schemaRegistryUrl) {
        setCachedSchemaRegistryClient(new CachedSchemaRegistryClient(schemaRegistryUrl, 10));
    }

    public static SchemaRegistryUtil getDefaultInstance() {
        return Optional.ofNullable(SCHEMA_REGISTRY_UTIL).orElse(new SchemaRegistryUtil());
    }

    public static SchemaRegistryUtil getInstance(final String schemaRegistryUrl) {
        return Optional.ofNullable(SCHEMA_REGISTRY_UTIL).orElse(new SchemaRegistryUtil(schemaRegistryUrl));
    }

    public CachedSchemaRegistryClient getCachedSchemaRegistryClient() {
        return cachedSchemaRegistryClient;
    }

    public static void setCachedSchemaRegistryClient(CachedSchemaRegistryClient registryClient) {
        cachedSchemaRegistryClient = registryClient;
    }

    public StructType getStructType(final String topicName) throws IOException, RestClientException {
        StructType structType;
        CachedSchemaRegistryClient registryClient = getCachedSchemaRegistryClient();
        try {
            SchemaMetadata latestSchemaMetadata = registryClient.getLatestSchemaMetadata(topicName + "-value");
            Schema schema = registryClient.getById(latestSchemaMetadata.getId());
            structType = (StructType) SchemaConverters.toSqlType(schema).dataType();
        } catch (Exception e) {
            log.error("Error while loading type based on schema", e);
            throw e;
        }
        return structType;
    }
}
