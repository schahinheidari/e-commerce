package org.example.shoppingbackend.service.user;

import org.example.shoppingbackend.model.dto.UserDto;
import org.example.shoppingbackend.model.entity.User;
import org.example.shoppingbackend.request.CreateUserRequest;
import org.example.shoppingbackend.request.UserUpdateRequest;

public interface UserServiceIMPL {
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long id);

    UserDto convertUserToUserDto(User user);

    User getAuthenticatedUser();
}
