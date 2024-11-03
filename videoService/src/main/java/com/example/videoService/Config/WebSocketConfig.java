package com.example.videoService.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.setMessageSizeLimit(10 * 1024 * 1024); // Adjust as needed
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/video-websocket/ws")
                .setAllowedOrigins("*"); // For development only - replace with specific origins in production
//                .setStreamBytesLimit(512 * 1024)
//                .setHttpMessageCacheSize(1000)
//                .setDisconnectDelay(30 * 1000);
    }
}