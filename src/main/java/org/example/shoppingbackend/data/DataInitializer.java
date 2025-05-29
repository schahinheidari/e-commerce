package org.example.shoppingbackend.data;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.model.entity.User;
import org.example.shoppingbackend.repository.UserDao;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final UserDao userDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createDefaultUserIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@example.com";
            if (userDao.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            userDao.save(user);
            System.out.println("Default vet user " + i + " created successfully");
        }
    }
}
