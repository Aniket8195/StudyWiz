package com.example.videoService.Repository;

import com.example.videoService.Entity.VideoStream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoStreamRepository extends JpaRepository<VideoStream,Long> {
    List<VideoStream> findByRoomId(Long roomId);
    VideoStream findByRoomIdAndUserId(Long roomId, Long userId);
}
