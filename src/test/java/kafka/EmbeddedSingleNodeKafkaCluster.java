package kafka;

import io.confluent.kafka.schemaregistry.RestApp;
import io.confluent.kafka.schemaregistry.avro.AvroCompatibilityLevel;
import kafka.server.KafkaConfig$;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.test.InstanceSpec;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import zookeeper.ZooKeeperEmbedded;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class EmbeddedSingleNodeKafkaCluster implements AfterEachCallback, BeforeEachCallback {

    private static final int DEFAULT_BROKER_PORT = 8092;
    private static final String KAFKA_SCHEMAS_TOPIC = "_schemas";
    private static final String AVRO_COMPATIBILITY_TYPE = AvroCompatibilityLevel.NONE.name;

    private ZooKeeperEmbedded zookeeper;
    private KafkaEmbedded broker;
    private RestApp schemaRegistry;
    private final Properties brokerConfig;

    public EmbeddedSingleNodeKafkaCluster() {
        this(new Properties());
    }

    public EmbeddedSingleNodeKafkaCluster(final Properties brokerConfig) {
        this.brokerConfig = new Properties();
        this.brokerConfig.putAll(brokerConfig);
    }

    private void start() throws Exception {
        log.debug("Initiating embedded Kafka cluster startup");
        log.debug("Starting a ZooKeeper instance...");
        zookeeper = new ZooKeeperEmbedded();
        log.debug("ZooKeeper instance is running at {}", zookeeperConnect());

        final Properties effectiveBrokerConfig = effectiveBrokerConfigFrom(brokerConfig, zookeeper);
        log.info("Starting a Kafka instance on port {} ...", effectiveBrokerConfig.getProperty(KafkaConfig$.MODULE$.PortProp()));
        broker = new KafkaEmbedded(effectiveBrokerConfig);
        log.debug("Kafka instance is running at {}, connected to ZooKeeper at {}", broker.brokerList(), broker.zookeeperConnect());

        schemaRegistry = new RestApp(7081, zookeeperConnect(), KAFKA_SCHEMAS_TOPIC, AVRO_COMPATIBILITY_TYPE, new Properties());
        schemaRegistry.start();
    }

    public String bootstrapServer() {
        return broker.brokerList();
    }

    public String schemaRegistryUrl() {
        return schemaRegistry.restConnect;
    }

    private Properties effectiveBrokerConfigFrom(final Properties brokerConfig, final ZooKeeperEmbedded zookeeper) {
        final Properties effectiveConfig = new Properties();
        effectiveConfig.putAll(brokerConfig);
        effectiveConfig.put(KafkaConfig$.MODULE$.ZkConnectProp(), zookeeper.connectString());
        effectiveConfig.put(KafkaConfig$.MODULE$.ZkSessionTimeoutMsProp(), 30 * 1000);
        effectiveConfig.put(KafkaConfig$.MODULE$.PortProp(), DEFAULT_BROKER_PORT);
        effectiveConfig.put(KafkaConfig$.MODULE$.ZkConnectionTimeoutMsProp(), 60 * 1000);
        effectiveConfig.put(KafkaConfig$.MODULE$.DeleteTopicEnableProp(), true);
        effectiveConfig.put(KafkaConfig$.MODULE$.LogCleanerDedupeBufferSizeProp(), 2 * 1024 * 1024L);
        effectiveConfig.put(KafkaConfig$.MODULE$.GroupMinSessionTimeoutMsProp(), 0);
        effectiveConfig.put(KafkaConfig$.MODULE$.OffsetsTopicReplicationFactorProp(), (short) 1);
        effectiveConfig.put(KafkaConfig$.MODULE$.OffsetsTopicPartitionsProp(), 1);
        effectiveConfig.put(KafkaConfig$.MODULE$.AutoCreateTopicsEnableProp(), true);
        return effectiveConfig;
    }

    private void stop() {
        log.info("Stopping Confluent");
        try {
            try {
                if (schemaRegistry != null) {
                    schemaRegistry.stop();
                }
            } catch (final Exception fatal) {
                throw new RuntimeException(fatal);
            }
            if (broker != null) {
                broker.stop();
            }
            try {
                if (zookeeper != null) {
                    zookeeper.stop();
                }
            } catch (final IOException fatal) {
                throw new RuntimeException(fatal);
            }
        } catch (Exception e) {
            log.error("Error while stooping Confluent Kafka", e);
        }
        log.info("Confluent Stopped");
    }

    public String zookeeperConnect() {
        return zookeeper.connectString();
    }

    public void createTopic(final String topic) {
        createTopic(topic, 1, (short) 1);
    }

    public void createTopic(final String topic, final int partitions, final short replication) {
        createTopic(topic, partitions, replication, Collections.emptyMap());
    }

    public void createTopic(final String topic,
                            final int partitions,
                            final short replication,
                            final Map<String, String> topicConfig) {
        broker.createTopic(topic, partitions, replication, topicConfig);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        stop();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        start();
    }
}
