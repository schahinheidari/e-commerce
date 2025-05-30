package org.example.shoppingbackend.security.user;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.model.entity.User;
import org.example.shoppingbackend.repository.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userDao.findByEmail(email))
                .orElseThrow(()-> new  UsernameNotFoundException("User not found"));
        return ShopUserDetails.buildUserDetails(user);
    }
}
