package com.example.roomService.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRegDTO {
    String name;
    String description;
    int maxMembers;
    boolean isPrivate;
    int ownerId;
}
