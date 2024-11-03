package com.example.videoService.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignalMessage {
    private String type; // "offer" | "answer" | "ice-candidate"
    private String from;
    private String to;
    private Object data;
}
