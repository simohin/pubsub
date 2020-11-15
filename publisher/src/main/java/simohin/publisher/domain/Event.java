package simohin.publisher.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simohin.publisher.enums.Action;

/**
 * @author Timofei Simohin
 * @created 15.11.2020
 */
@RequiredArgsConstructor
public class Event implements Serializable {

    private static final long serialVersionUID = -7977842382688539506L;

    @NonNull
    @JsonProperty("id")
    private final Long id;

    @NonNull
    @JsonProperty("msisdn")
    private final Long msisdn;

    @NonNull
    @JsonProperty("action")
    private final Action action;

    @NonNull
    @JsonProperty("timestamp")
    private final Long timestamp;
}
