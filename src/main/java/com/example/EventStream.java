package com.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.sse.Event;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class EventStream {

    @Get(value = "/real/events", produces = MediaType.TEXT_EVENT_STREAM)
    public Flux<Event<String>> events() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> Event.of("EventNo: " + i).name("TYPE1"));
    }
}
