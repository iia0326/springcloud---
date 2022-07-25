package com.shop.service.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.WebSession;

import java.time.Duration;

@Service
public class SessionGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            WebSession session = exchange.getSession().block();
            session.setMaxIdleTime(Duration.ofDays(7));
            return chain.filter(exchange);
        };
    }

}
