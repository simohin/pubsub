package simohin.subscriber.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import simohin.subscriber.entity.Event;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
@RestController
@RequiredArgsConstructor
public class EventController {

    @PostMapping("/event")
    public void event(@RequestBody Event payload) {

        System.out.println();

    }

}
