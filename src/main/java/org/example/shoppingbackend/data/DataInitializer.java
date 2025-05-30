package org.example.shoppingbackend.data;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.model.entity.Role;
import org.example.shoppingbackend.model.entity.User;
import org.example.shoppingbackend.repository.RoleDao;
import org.example.shoppingbackend.repository.UserDao;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 1. First create roles if they don't exist
        createDefaultRoles();

        // 2. Then create admin users
        createDefaultAdminIfNotExists();

        // 3. Finally create regular users
        createDefaultUserIfNotExists();
    }

    private void createDefaultRoles() {
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_USER");
    }

    private void createRoleIfNotExists(String roleName) {
        if (!roleDao.existsByName(roleName)) {
            Role role = new Role(roleName);
            roleDao.save(role);
            System.out.println("Created role: " + roleName);
        }
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleDao.findByName("ROLE_USER")
                .orElseGet(() -> {
                    System.out.println("ROLE_USER not found, creating it now");
                    return roleDao.save(new Role("ROLE_USER"));
                });

        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@example.com";
            if (userDao.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userDao.save(user);
            System.out.println("Default user " + i + " created successfully");
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleDao.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    System.out.println("ROLE_ADMIN not found, creating it now");
                    return roleDao.save(new Role("ROLE_ADMIN"));
                });

        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@example.com";
            if (userDao.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userDao.save(user);
            System.out.println("Default admin user " + i + " created successfully");
        }
    }
}
