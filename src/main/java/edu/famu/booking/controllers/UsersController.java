package edu.famu.booking.controllers;

import edu.famu.booking.service.UserService;
import edu.famu.booking.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UsersController {
    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers()
    {
        try {
            return ResponseEntity.ok(new ApiResponse(true,"Success", userService.getAllUsers(), null));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(false, "An error occurred.", null, e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUsersById(@PathVariable String userId){
        try {
            return ResponseEntity.ok(new ApiResponse(true,"Success", userService.getUsersById(userId), null));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(false, "An error occurred.", null, e.getMessage()));
        }
    }
}
