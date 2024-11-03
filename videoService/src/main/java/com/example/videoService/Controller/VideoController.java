package com.example.videoService.Controller;

import com.example.videoService.DTO.SignalMessage;
import com.example.videoService.Entity.Video;
import com.example.videoService.Entity.VideoStream;
import com.example.videoService.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("video-service")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/room/{roomId}/join")
    public void handleJoinRoom(@DestinationVariable Long roomId, Principal principal) {
        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/userJoined", principal.getName());
    }

    @MessageMapping("/room/{roomId}/leave")
    public void handleLeaveRoom(@DestinationVariable Long roomId, Principal principal) {
        videoService.stopStream(roomId, Long.parseLong(principal.getName()));
        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/userLeft", principal.getName());
    }

    @MessageMapping("/room/{roomId}/signal")
    public void handleSignal(
            @DestinationVariable Long roomId,
            @Payload SignalMessage signal,
            Principal principal
    ) {
        signal.setFrom(principal.getName());
        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/signal/" + signal.getTo(), signal);
    }
    // Start a video stream
    @PostMapping("/{roomId}/streams")
    public ResponseEntity<Map<String, Object>> startStream(@PathVariable Long roomId, @RequestParam Long userId, @RequestParam String streamUrl) {
        try {
            VideoStream stream = videoService.startStream(roomId, userId, streamUrl);
            return new ResponseEntity<>(Map.of("stream", stream), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Unable to start stream: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Stop a video stream
    @DeleteMapping("/{roomId}/streams")
    public ResponseEntity<Map<String, Object>> stopStream(@PathVariable Long roomId, @RequestParam Long userId) {
        try {
            boolean isStopped = videoService.stopStream(roomId, userId);
            if (isStopped) {
                return new ResponseEntity<>(Map.of("message", "Stream stopped successfully"), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(Map.of("error", "Stream not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Unable to stop stream: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all active streams for a room
    @GetMapping("/{roomId}/streams")
    public ResponseEntity<Map<String, Object>> getActiveStreams(@PathVariable Long roomId) {
        try {
            List<VideoStream> streams = videoService.getActiveStreams(roomId);
            return new ResponseEntity<>(Map.of("streams", streams), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Unable to retrieve streams: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // WebSocket endpoint for signaling
    @MessageMapping("/stream")
    @SendTo("/topic/streams")
    public VideoStream handleStream(VideoStream stream) {
        return stream; // Broadcast the stream information
    }
}
