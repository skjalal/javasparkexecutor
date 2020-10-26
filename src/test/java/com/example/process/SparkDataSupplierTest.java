package com.example.process;

import com.example.config.SparkProperties;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.DataStreamReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
class SparkDataSupplierTest {

    private static final String TEST = "TEST";

    @InjectMocks
    SparkDataSupplier sparkDataSupplier;

    @Mock
    SparkProperties sparkProperties;

    @Mock
    SparkSession sparkSession;

    @Mock
    Dataset<Row> dataset;

    @Mock
    DataStreamReader dataStreamReader;

    @Test
    void testGet() {
        Mockito.doReturn(TEST).when(sparkProperties).getPropertyValue(anyString());
        Mockito.doReturn(dataStreamReader).when(sparkSession).readStream();
        Mockito.doReturn(dataStreamReader).when(dataStreamReader).format(anyString());
        Mockito.doReturn(dataStreamReader).when(dataStreamReader).option(anyString(), anyString());
        Mockito.doReturn(dataset).when(dataStreamReader).load();
        Mockito.doNothing().when(dataset).printSchema();
        sparkDataSupplier.get();
        Mockito.verify(dataset, Mockito.timeout(1)).printSchema();
    }
}