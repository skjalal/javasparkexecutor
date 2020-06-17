package zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.test.TestingServer;

import java.io.IOException;

@Slf4j
public class ZooKeeperEmbedded {

    private final TestingServer server;

    public ZooKeeperEmbedded() throws Exception {
        log.debug("Starting embedded ZooKeeper server...");
        this.server = new TestingServer(1181);
        log.info("Embedded ZooKeeper server at {} uses the temp directory at {}", server.getConnectString(), server.getTempDirectory());
    }

    public void stop() throws IOException {
        log.debug("Shutting down embedded ZooKeeper server at {} ...", server.getConnectString());
        server.close();
        log.debug("Shutdown of embedded ZooKeeper server at {} completed", server.getConnectString());
    }

    public String connectString() {
        return server.getConnectString();
    }
}
