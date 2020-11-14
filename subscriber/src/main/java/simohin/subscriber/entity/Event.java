package simohin.subscriber.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;
import simohin.subscriber.enums.Action;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
@Data
public class Event implements Serializable {

    private static final long serialVersionUID = -7977842382688539506L;

    @NonNull
    private Long id;

    @NonNull
    private Long msisdn;

    @NonNull
    private Action action;

    @NonNull
    private Long timestamp;

}
