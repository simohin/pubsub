package simohin.subscriber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import simohin.subscriber.entity.Subscription;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
