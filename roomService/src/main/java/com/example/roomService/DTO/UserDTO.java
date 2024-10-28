package com.example.roomService.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    Long Id;
    String username;
    String email;
    String password;
}
