package com.example.process;

import com.example.config.SparkConfiguration;
import com.example.config.SparkProperties;
import com.example.model.Employee;
import org.apache.avro.Schema;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.avro.SchemaConverters;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SparkDataConsumerTest {

    private SparkDataConsumer sparkDataConsumer;
    private SparkSession sparkSession;

    @Autowired
    SparkProperties sparkProperties;

    @Autowired
    SparkConfiguration sparkConfiguration;

    @BeforeEach
    void setUp() {
        sparkDataConsumer = Mockito.spy(new SparkDataConsumer(sparkProperties));
        sparkSession = sparkConfiguration.getSparkSession();
    }

    @Test
    @Disabled
    void testAccept() {
        sparkDataConsumer.accept(employeeDataset());
        Mockito.verify(sparkDataConsumer).accept(ArgumentMatchers.any());
    }

    private Dataset<Employee> employeeDataset() {
        String employeeSchema = "{\"type\":\"record\"," +
                "\"name\":\"Employee\"," +
                "\"fields\":[{\"name\":\"employeeId\",\"type\":\"int\"}, {\"name\":\"employeeName\",\"type\":\"string\"}, {\"name\":\"designation\",\"type\":\"string\"}]}";
        Schema schema = new Schema.Parser().parse(employeeSchema);
        StructType structType = (StructType) SchemaConverters.toSqlType(schema).dataType();
        Dataset<Employee> employeeDataset = sparkSession.readStream()
                .format("json")
                .schema(structType)
                .json("src/test/resources/json")
                .as(Encoders.bean(Employee.class));
        employeeDataset.printSchema();
        return employeeDataset;
    }

    @AfterEach
    void tearDown() {
        sparkConfiguration.closeSparkSession();
    }
}