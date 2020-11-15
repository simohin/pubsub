package simohin.publisher.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import simohin.publisher.domain.Event;
import simohin.publisher.enums.Action;

/**
 * @author Timofei Simohin
 * @created 15.11.2020
 */
@Service
@Slf4j
public class PublishingService extends Thread implements InitializingBean {

    protected final IdGeneratorService idGenerator;

    private final ExecutorService pool;

    @Value("${publisher.interThreadSleep}")
    private Long interThreadSleep;

    private final URI subscriberUrl;

    public PublishingService(IdGeneratorService idGenerator,
            @Value("${publisher.poolSize}") Integer poolSize,
            @Value("${subscriber.url}") String subscriberUrl) throws URISyntaxException {

        this.idGenerator = idGenerator;

        this.pool = Executors.newFixedThreadPool(poolSize);

        this.subscriberUrl = new URI(subscriberUrl);
    }

    @Override
    public void run() {

        for (;;) {
            var id = idGenerator.getNextId();
            pool.execute(new Publishing(id, interThreadSleep, subscriberUrl));
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

        private final URI url;

        private final Random r = new Random();

        private final RestTemplate restTemplate = new RestTemplate();

        private final ObjectMapper m = new ObjectMapper();

        public Publishing(Long id, Long sleep, URI url) {

            this.id = id;
            this.sleep = sleep;
            this.url = url;
        }

        @Override
        public void run() {

            try {
                publish(id);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        public void publish(Long id) throws InterruptedException, JsonProcessingException {

            log.info("Publishing event id={} in thread {}", id, Thread.currentThread().getName());

            var values = Action.values();

            var e = new Event(id, ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE),
                    values[r.nextInt(values.length)], System.currentTimeMillis());
            String json = m.writeValueAsString(e);

            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            var entity = new HttpEntity<>(json, headers);

            restTemplate.postForObject(url, entity, HttpEntity.class);

            Thread.sleep(sleep);
        }
    }

}
