package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.dto.request.UserRegistrationRequestDTO;
import com.example.hotelmanagementsystem.dto.response.UserResponseDTO;
import com.example.hotelmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegistrationRequestDTO requestDTO){
        UserResponseDTO responseDTO = userService.registerUser(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO responseDTO = userService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("/search/username")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@RequestParam String username){
        UserResponseDTO responseDTO = userService.findByUsername(username);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/search/email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email){
        UserResponseDTO responseDTO = userService.findByEmail(email);
        return ResponseEntity.ok(responseDTO);
    }
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<UserResponseDTO> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<UserResponseDTO> reactivateUser(@PathVariable Long id) {
        UserResponseDTO responseDTO = userService.reactivateUser(id);
        return ResponseEntity.ok(responseDTO);
    }
    @PatchMapping("/{id}/profile")
    public ResponseEntity<UserResponseDTO> updateUserProfile(@PathVariable Long id, @RequestBody UserRegistrationRequestDTO requestDTO) {
        UserResponseDTO responseDTO = userService.updateProfile(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
