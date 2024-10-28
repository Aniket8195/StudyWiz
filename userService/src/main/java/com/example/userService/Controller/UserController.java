package com.example.userService.Controller;

import com.example.userService.DTO.LoginDTO;
import com.example.userService.DTO.RegisterDTO;
import com.example.userService.Entity.User;
import com.example.userService.Repository.UserRepository;
import com.example.userService.Service.UserService;
import com.example.userService.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO user) {
        try{
            if(userRepository.findByEmail(user.getEmail()) != null){
                return ResponseEntity.badRequest().body("Email already exists");
            }
            User user1=new User();

            user1.setEmail(user.getEmail());
            user1.setUsername(user.getUsername());
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
            User user2=userService.register(user1);

            return ResponseEntity.ok(Map.of(

                    "message", "User registered successfully",
                    "data", user2
                    )
            );

        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO user) {
       try {
          if(userRepository.findByEmail(user.getEmail()) == null){
              return ResponseEntity.badRequest().body("Email does not exist");

          }
          String token= jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(Map.of(
                    "message", "User logged in successfully",
                    "token", token
            ));

       }catch ( Exception e){
           return new ResponseEntity("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
       }


    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
         try{
             return  userService.getUser(userId);

         }catch (Exception e){
             return null;
         }


    }
}
