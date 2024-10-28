package com.example.roomService.Controller;

import com.example.roomService.DTO.RoomRegDTO;
import com.example.roomService.DTO.UserDTO;
import com.example.roomService.Entity.Room;
import com.example.roomService.Entity.RoomMember;
import com.example.roomService.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    // Create a new room
    @PostMapping
    public ResponseEntity<Map<String, Object>> createRoom(@RequestBody RoomRegDTO room) {
        try {
            Room room1=new Room();
            room1.setName(room.getName());
            room1.setDescription(room.getDescription());
            room1.setMaxMembers(room.getMaxMembers());
            room1.setPrivate(room.isPrivate());

            Room createdRoom = roomService.createRoom(room1, (long) room.getOwnerId());
            return new ResponseEntity<>(Map.of("room", createdRoom), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to create room", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a room by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRoomById(@PathVariable Long id) {
        try {
            Room room = roomService.getRoomById(id);
            if (room != null) {
                return new ResponseEntity<>(Map.of("room", room), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Room not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to retrieve room", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all rooms
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRooms() {
        try {
            List<Room> rooms = roomService.getAllRooms();
            return new ResponseEntity<>(Map.of("rooms", rooms), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to retrieve rooms", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a room
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        try {
            Room updatedRoom = roomService.updateRoom(id, roomDetails);
            if (updatedRoom != null) {
                return new ResponseEntity<>(Map.of("room", updatedRoom), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Room not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to update room", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a room
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRoom(@PathVariable Long id, @RequestBody int ownerId) {
        try {
            boolean isDeleted = roomService.deleteRoom(id,(long)ownerId);
            if (isDeleted) {
                return new ResponseEntity<>(Map.of("message", "Room deleted successfully"), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(Map.of("error", "Room not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to delete room", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add a member to a room
    @PostMapping("/{roomId}/members")
    public ResponseEntity<Map<String, Object>> addRoomMember(@PathVariable Long roomId, @RequestBody RoomMember roomMember) {
        try {
            RoomMember addedMember = roomService.addRoomMember(roomId, roomMember);
            return new ResponseEntity<>(Map.of("member", addedMember), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to add room member", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all members of a room
    @GetMapping("/{roomId}/members")
    public ResponseEntity<Map<String, Object>> getRoomMembers(@PathVariable Long roomId) {
        try {
            List<UserDTO> members = roomService.getRoomMembers(roomId);
            return new ResponseEntity<>(Map.of("members", members), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to retrieve room members", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}