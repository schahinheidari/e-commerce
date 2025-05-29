package org.example.shoppingbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.exceptions.AlreadyExistsException;
import org.example.shoppingbackend.exceptions.ResourceNotFoundException;
import org.example.shoppingbackend.model.dto.UserDto;
import org.example.shoppingbackend.model.entity.User;
import org.example.shoppingbackend.request.CreateUserRequest;
import org.example.shoppingbackend.request.UserUpdateRequest;
import org.example.shoppingbackend.response.ApiResponse;
import org.example.shoppingbackend.service.user.UserServiceIMPL;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserServiceIMPL userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User Success", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long id) {
        try {
            User user = userService.updateUser(request, id);
            UserDto userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("Delete User Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
