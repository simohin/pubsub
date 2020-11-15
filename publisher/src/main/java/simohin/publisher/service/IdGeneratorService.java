package simohin.publisher.service;

import org.springframework.stereotype.Service;

/**
 * @author Timofei Simohin
 * @created 15.11.2020
 */
@Service
public class IdGeneratorService {

    private volatile Long nextId = 0L;

    public synchronized Long getNextId() {

        return nextId++;
    }

}
