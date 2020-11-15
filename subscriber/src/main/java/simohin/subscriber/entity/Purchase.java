package simohin.subscriber.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
@Entity
@Table(name = "purchase")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class Purchase implements EventEntity{

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "msisdn")
    private Long msisdn;

    @Column(name = "timestamp")
    private Long timestamp;

}
