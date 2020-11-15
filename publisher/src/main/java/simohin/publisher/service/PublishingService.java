package simohin.publisher.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Timofei Simohin
 * @created 15.11.2020
 */
@Service
@Slf4j
public class PublishingService extends Thread implements InitializingBean {

    protected final IdGeneratorService idGenerator;

    private final ExecutorService pool;

    private final Long interThreadSleep;

    public PublishingService(IdGeneratorService idGenerator) {

        this.idGenerator = idGenerator;

        this.pool = Executors.newFixedThreadPool(5);

        this.interThreadSleep = 100L;
    }

    @Override
    public void run() {

        for (;;) {
            var id = idGenerator.getNextId();
            pool.execute(new Publishing(id, interThreadSleep));
        }
    }

    @Override
    public void afterPropertiesSet() {

        log.info("Running publishing");
        start();
    }

    static class Publishing implements Runnable {

        private final Long id;

        private final Long sleep;

        public Publishing(Long id, Long sleep) {

            this.id = id;
            this.sleep = sleep;
        }

        @Override
        public void run() {

            try {
                publish(id);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

        public void publish(Long id) throws InterruptedException {

            log.info("Publishing event id={} in thread {}", id, Thread.currentThread().getName());
            Thread.sleep(sleep);
        }
    }

}
