package com.example.roomService.Repository;

import com.example.roomService.Entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember,Long> {
    List<RoomMember> findByRoomId(Long roomId);
    RoomMember findByRoomIdAndUserId(Long roomId, Long userId);
}
