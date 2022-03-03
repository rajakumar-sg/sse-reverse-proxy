package com.example;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.sse.SseClient;
import io.micronaut.http.sse.Event;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.test.StepVerifier;

@MicronautTest
class EventStreamTest {

    @Inject
    @Client("/")
    SseClient client;

    @Test
    void eventsReceived() {
        Publisher<Event<String>> events = client.eventStream("/real/events", String.class);
        StepVerifier.create(events)
                .assertNext(event -> Assertions.assertEquals("EventNo: 1", event.getData()))
                .assertNext(event -> Assertions.assertEquals("EventNo: 2", event.getData()))
                .assertNext(event -> Assertions.assertEquals("EventNo: 3", event.getData()))
                .assertNext(event -> Assertions.assertEquals("EventNo: 4", event.getData()))
                .assertNext(event -> Assertions.assertEquals("EventNo: 5", event.getData()))
                .assertNext(event -> Assertions.assertEquals("EventNo: 6", event.getData()))
                .assertNext(event -> Assertions.assertEquals("EventNo: 7", event.getData()))
                .assertNext(event -> Assertions.assertEquals("EventNo: 8", event.getData()))
                .assertNext(event -> Assertions.assertEquals("EventNo: 9", event.getData()))
                .assertNext(event -> Assertions.assertEquals("EventNo: 10", event.getData()))
                .verifyComplete();
    }
}