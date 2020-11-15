package simohin.subscriber.processor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import simohin.subscriber.domain.Event;
import simohin.subscriber.entity.Subscription;
import simohin.subscriber.repository.SubscriptionRepository;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
@Component
@Qualifier(value = "subscriptionProcessor")
@Slf4j
public class SubscriptionProcessor implements EventProcessor<Subscription> {

    private final SubscriptionRepository repository;

    public SubscriptionProcessor(SubscriptionRepository repository) {

        this.repository = repository;
    }

    @Override
    public void process(Event e) {

        log.info("Processed subscription: {}", doProcess(e, repository).toString());

    }

    @Override
    public Subscription getEntity(Event e) {

        return new Subscription(e.getId(), e.getMsisdn(), e.getTimestamp());
    }
}
