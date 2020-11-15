package simohin.subscriber.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import simohin.subscriber.domain.Event;
import simohin.subscriber.entity.Purchase;
import simohin.subscriber.entity.Subscription;
import simohin.subscriber.processor.EventProcessor;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
@Slf4j
@RestController
public class EventController {

    private final EventProcessor<Purchase> purchaseProcessor;

    private final EventProcessor<Subscription> subscriptionProcessor;

    public EventController(
            @Qualifier(value = "purchaseProcessor") EventProcessor<Purchase> purchaseProcessor,
            @Qualifier(value = "subscriptionProcessor") EventProcessor<
                    Subscription> subscriptionProcessor) {

        this.purchaseProcessor = purchaseProcessor;
        this.subscriptionProcessor = subscriptionProcessor;
    }

    @PostMapping("/event")
    public void event(@RequestBody Event e) {

        log.info("Got event: {}", e.toString());

        switch (e.getAction()) {
            case PURCHASE:
                purchaseProcessor.process(e);
                break;
            case SUBSCRIPTION:
                subscriptionProcessor.process(e);
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected action: " + e.getAction().toString());
        }

    }

}
