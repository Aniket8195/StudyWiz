package com.example.roomService.Service;

import com.example.roomService.DTO.UserDTO;
import com.example.roomService.Entity.Room;
import com.example.roomService.Entity.RoomMember;
import com.example.roomService.Repository.RoomMemberRepository;
import com.example.roomService.Repository.RoomRepository;
import com.example.roomService.Repository.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMemberRepository roomMemberRepository;

    @Autowired
    private UserClient userClient; // Injecting the UserClient

    public Room createRoom(Room room, Long ownerId) {
        room = roomRepository.save(room);
        RoomMember member = new RoomMember();
        member.setRoom(room);
        member.setUserId(ownerId); // Set owner user ID
        roomMemberRepository.save(member);
        return room;
    }

    public void joinRoom(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Check if the room is full or not
        if (room.getMembers().size() >= room.getMaxMembers() && room.isPrivate()) {
            throw new RuntimeException("Room is full or private");
        }

        RoomMember member = new RoomMember();
        member.setRoom(room);
        member.setUserId(userId); // Set user ID
        roomMemberRepository.save(member);
    }

    public List<UserDTO> getRoomMembers(Long roomId) {
        List<RoomMember> members = roomMemberRepository.findByRoomId(roomId);
        return members.stream()
                .map(member -> userClient.getUserById(member.getUserId()))
                .collect(Collectors.toList());
    }

    public boolean deleteRoom(Long roomId, Long ownerId) {
       try{
           Room room = roomRepository.findById(roomId)
                   .orElseThrow(() -> new RuntimeException("Room not found"));

           // Check if the requester is the owner
//           RoomMember ownerMember = roomMemberRepository.findByRoomIdAndUserId(roomId, ownerId);
//           if (ownerMember == null) {
//               throw new RuntimeException("Only the owner can delete the room");
//           }

           roomRepository.delete(room);
           return true;
       }catch ( Exception e) {

           return false;
       }
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setName(roomDetails.getName());
        room.setDescription(roomDetails.getDescription());
        room.setMaxMembers(roomDetails.getMaxMembers());
        room.setPrivate(roomDetails.isPrivate());

        return roomRepository.save(room);
    }

    public RoomMember addRoomMember(Long roomId, RoomMember roomMember) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        roomMember.setRoom(room);
        return roomMemberRepository.save(roomMember);
    }

    public List<Room> getRoomsByUserId(Long userId) {
        List<RoomMember> roomMembers = roomMemberRepository.findByUserId(userId);
        return roomMembers.stream()
                .map(RoomMember::getRoom)
                .collect(Collectors.toList());
    }
}
