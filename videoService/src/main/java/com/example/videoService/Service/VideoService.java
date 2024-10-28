package com.example.videoService.Service;

import com.example.videoService.Entity.Video;
import com.example.videoService.Entity.VideoStream;
import com.example.videoService.Repository.VideoRepository;
import com.example.videoService.Repository.VideoStreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoStreamRepository videoStreamRepository;

    // Start a video stream for a participant
    public VideoStream startStream(Long roomId, Long userId, String streamUrl) {
        VideoStream stream = new VideoStream();
        stream.setRoomId(roomId);
        stream.setUserId(userId);
        stream.setStreamUrl(streamUrl);
        return videoStreamRepository.save(stream);
    }

    // Stop a video stream for a participant
    public boolean stopStream(Long roomId, Long userId) {
        VideoStream stream = videoStreamRepository.findByRoomIdAndUserId(roomId, userId);
        if (stream != null) {
            videoStreamRepository.delete(stream);
            return true;
        }
        return false;
    }

    // Get all active streams for a room
    public List<VideoStream> getActiveStreams(Long roomId) {
        return videoStreamRepository.findByRoomId(roomId);
    }
}
