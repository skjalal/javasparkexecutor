package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

@Slf4j
public class JavaSparkExecutor {

    public static void main(String[] args) {
        log.info("This is Java Spark Standalone Application");
        new JavaSparkExecutor().sparkExecute();
    }

    public void sparkExecute() {
        SparkConf sparkConf = new SparkConf().setAppName("DataFrameExample").setMaster("local[*]");
        SparkSession sparkSession = SparkSession.builder().config(sparkConf).config("spark.sql.warehouse.dir", "D:/temp").getOrCreate();
        String logName = sparkSession.logName();
        log.info("Spark Logger Name: {}", logName);
        sparkSession.close();
    }
}
