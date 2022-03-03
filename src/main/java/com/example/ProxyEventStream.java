package com.example;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.client.ProxyHttpClient;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.runtime.server.EmbeddedServer;
import org.reactivestreams.Publisher;

@Filter("/proxy/**")
public class ProxyEventStream implements HttpServerFilter {

    private final ProxyHttpClient client;
    private final EmbeddedServer embeddedServer;

    public ProxyEventStream(ProxyHttpClient client,
                       EmbeddedServer embeddedServer) {
        this.client = client;
        this.embeddedServer = embeddedServer;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request,
                                                      ServerFilterChain chain) {
        return Publishers.map(client.proxy( //
                request.mutate() //
                        .uri(b -> b //
                                .scheme("http")
                                .host(embeddedServer.getHost())
                                .port(embeddedServer.getPort())
                                .replacePath(StringUtils.prependUri(
                                        "/real",
                                        request.getPath().substring("/proxy".length())
                                ))
                        )
                        .header("X-My-Request-Header", "XXX") //
        ), response -> response.header("X-My-Response-Header", "YYY"));
    }
}