package com.example.util;

public final class SparkConstant {

    public static final String SPARK_DEFAULT_APP_NAME = "SparkAppDefault";
    public static final String LOCAL = "local";
    public static final String SPARK_MASTER = "spark.master";
    public static final String SPARK_APP_NAME = "spark.appName";
    public static final String SPARK_DEFAULT_PROPERTY_DRIVER_MEMORY = "spark.defaultProperty.driver.memory";
    public static final String SPARK_DEFAULT_PROPERTY_DRIVER_MAX_RESULT_SIZE = "spark.defaultProperty.driver.maxResultSize";
    public static final String SPARK_DEFAULT_PROPERTY_DRIVER_CORES = "spark.defaultProperty.driver.cores";
    public static final String SPARK_DEFAULT_PROPERTY_LOCAL_DIR = "spark.defaultProperty.local.dir";
    public static final String SPARK_SQL_SHUFFLE_PARTITIONS = "spark.sql.shuffle.partitions";
    public static final String SPARK_SQL_WAREHOUSE_DIR = "spark.sql.warehouse.dir";
    public static final String SPARK_STREAMING_DURATION = "spark.streaming.duration";
    public static final String SPARK_STREAMING_QUERY_TIME_OUT_MS = "spark.streaming.query.timeout";
    public static final String DESERIALIZE = "deserialize";
    public static final String DESERIALIZE_AS_VALUE = "deserialize(value) as value";
    public static final String CAST_KEY_AS_STRING_AS_KEY = "cast(key as string) as key";
    public static final String KEY = "key";
    public static final String JSON_VALUE_STAR = "json_value.*";
    public static final String JSON_VALUE = "json_value";
    public static final String VALUE = "value";
    public static final String EMPLOYEES_VIEW = "employees_view";
    public static final String SQL = "select * from employees_view";
    public static final String CHECKPOINT_LOCATION = "checkPointLocation";
    public static final String UPDATE_MODE = "update";
    public static final String SPARK_KAFKA_CHECKPOINT_LOCATION = "spark.kafka.checkPointLocation";

    private SparkConstant() {
    }
}
