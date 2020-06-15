package com.example.process;

import com.example.config.SparkProperties;
import com.example.model.Employee;
import com.example.util.KafkaConstant;
import com.example.util.SchemaRegistryUtil;
import com.example.util.SparkConstant;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import java.util.function.Function;

@Slf4j
public class SparkDataHandler implements Function<Dataset<Row>, Dataset<Employee>> {

    private final SparkSession sparkSession;
    private final StructType structType;

    public SparkDataHandler(SparkSession sparkSession, SparkProperties sparkProperties) {
        this.sparkSession = sparkSession;
        String schemaRegistryUrl = sparkProperties.getPropertyValue(KafkaConstant.KAFKA_SCHEMA_REGISTRY_URL);
        SchemaRegistryUtil schemaRegistryUtil = SchemaRegistryUtil.getInstance(schemaRegistryUrl);
        this.structType = schemaRegistryUtil.getStructType(KafkaConstant.KAFKA_TOPIC);

        sparkSession.udf().register(SparkConstant.DESERIALIZE,
                (byte[] data) -> {
                    CachedSchemaRegistryClient cachedSchemaRegistryClient = SchemaRegistryUtil.getDefaultInstance().getCachedSchemaRegistryClient();
                    try (KafkaAvroDeserializer kafkaAvroDeserializer = new KafkaAvroDeserializer(cachedSchemaRegistryClient)) {
                        return kafkaAvroDeserializer.deserialize("", data).toString();
                    }
                }, DataTypes.StringType);
    }

    @Override
    public Dataset<Employee> apply(Dataset<Row> rowDataset) {
        log.info("Starting Data Transformation...!");
        Dataset<Row> dataset = rowDataset
                .selectExpr(SparkConstant.CAST_KEY_AS_STRING_AS_KEY, SparkConstant.DESERIALIZE_AS_VALUE)
                .select(functions.col(SparkConstant.KEY), functions.from_json(functions.col(SparkConstant.VALUE), structType).alias(SparkConstant.JSON_VALUE))
                .select(SparkConstant.JSON_VALUE_STAR);

        dataset.printSchema();
        dataset.createOrReplaceTempView(SparkConstant.EMPLOYEES_VIEW);
        return sparkSession.sql(SparkConstant.SQL).as(Encoders.bean(Employee.class));
    }
}
