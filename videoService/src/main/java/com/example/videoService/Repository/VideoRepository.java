package com.example.videoService.Repository;

import com.example.videoService.Entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video,Long> {
    List<Video> findByRoomId(Long roomId);
}
