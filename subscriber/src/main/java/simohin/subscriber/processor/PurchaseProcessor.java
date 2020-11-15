package simohin.subscriber.processor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import simohin.subscriber.domain.Event;
import simohin.subscriber.entity.Purchase;
import simohin.subscriber.repository.PurchaseRepository;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
@Component
@Qualifier(value = "purchaseProcessor")
@Slf4j
public class PurchaseProcessor implements EventProcessor<Purchase> {

    private final PurchaseRepository repository;

    public PurchaseProcessor(PurchaseRepository repository) {

        this.repository = repository;
    }

    @Override
    public void process(Event e) {

        log.info("Processed purchase: {}", doProcess(e, repository).toString());

    }

    @Override
    public Purchase getEntity(Event e) {

        return new Purchase(e.getId(), e.getMsisdn(), e.getTimestamp());
    }
}
