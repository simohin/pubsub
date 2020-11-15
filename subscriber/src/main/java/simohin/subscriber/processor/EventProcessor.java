package simohin.subscriber.processor;

import org.springframework.data.jpa.repository.JpaRepository;

import simohin.subscriber.domain.Event;
import simohin.subscriber.entity.EventEntity;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
public interface EventProcessor<T extends EventEntity> {

    void process(Event e);

    T getEntity(Event e);

    default T doProcess(Event e, JpaRepository<T, Long> repository) {

        var byId = repository.findById(e.getId());
        T entity;
        if (byId.isEmpty()) {
            entity = getEntity(e);
        } else {
            entity = byId.get();
            entity.setId(e.getId());
            entity.setMsisdn(e.getMsisdn());
            entity.setTimestamp(e.getTimestamp());
        }
        return repository.save(entity);
    }

}
