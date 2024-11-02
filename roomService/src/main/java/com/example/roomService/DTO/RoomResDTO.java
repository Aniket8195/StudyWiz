package com.example.roomService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResDTO {
    private Long id;
    private String name;
    private String description;
    private int maxMembers;
    private boolean isPrivate;

}
