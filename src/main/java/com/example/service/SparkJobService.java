package com.example.service;

import com.example.config.SparkConfiguration;
import com.example.config.SparkProperties;
import com.example.model.Employee;
import com.example.process.SparkDataConsumer;
import com.example.process.SparkDataHandler;
import com.example.process.SparkDataSupplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Service
public class SparkJobService {

    private final Supplier<Dataset<Row>> datasetSupplier;
    private final Function<Dataset<Row>, Dataset<Employee>> datasetFunction;
    private final Consumer<Dataset<Employee>> datasetConsumer;

    public SparkJobService(SparkProperties sparkProperties, SparkConfiguration sparkConfiguration) {
        SparkSession sparkSession = sparkConfiguration.getSparkSession();
        this.datasetSupplier = new SparkDataSupplier(sparkSession, sparkProperties);
        this.datasetFunction = new SparkDataHandler(sparkSession, sparkProperties);
        this.datasetConsumer = new SparkDataConsumer(sparkProperties);
    }

    public void execute() {
        log.info("Executing Spark Jobs...!");
        datasetConsumer.accept(datasetFunction.apply(datasetSupplier.get()));
    }
}