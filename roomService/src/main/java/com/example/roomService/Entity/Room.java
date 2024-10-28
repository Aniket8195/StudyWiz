package com.example.roomService.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int maxMembers;
    private boolean isPrivate;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomMember> members;

    // Getters and Setters
}