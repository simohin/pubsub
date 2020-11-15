package simohin.subscriber.entity;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
public interface EventEntity {

    void setTimestamp(Long timestamp);

    void setMsisdn(Long msisdn);

    void setId(Long id);
}
