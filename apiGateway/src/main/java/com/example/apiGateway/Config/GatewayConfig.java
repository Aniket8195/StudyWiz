package com.example.apiGateway.Config;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final DiscoveryClient discoveryClient;

    public GatewayConfig(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        String videoServiceUri = discoveryClient.getInstances("video-service") // Replace with your service name
                .stream()
                .findFirst()
                .map(serviceInstance -> "ws://" + serviceInstance.getHost() + ":" + serviceInstance.getPort())
                .orElseThrow(() -> new RuntimeException("Video service not found"));

        return builder.routes()
                // Add route for WebSocket connections
                .route("video_service_websocket", r -> r
                        .path("/video-service/**","/video-websocket") // Match the WebSocket endpoint
                        .uri(videoServiceUri)

                )
                .build();
    }
}

