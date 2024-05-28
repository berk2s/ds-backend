package com.berk2s.ds.api.infrastructure.common;

import com.berk2s.ds.api.infrastructure.user.jpa.RoleEntity;
import com.berk2s.ds.api.infrastructure.user.jpa.RoleJPARepository;
import com.berk2s.ds.api.infrastructure.user.jpa.UserEntity;
import com.berk2s.ds.api.infrastructure.user.jpa.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@RequiredArgsConstructor
@Slf4j
@Component
public class InitialDataLoader implements CommandLineRunner {
    private final InitialDataConfiguration initialDataConfiguration;
    private final UserJPARepository userJPARepository;
    private final RoleJPARepository roleJPARepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        initialDataConfiguration
                .getRoles()
                .stream()
                .filter((i) -> !roleJPARepository.existsByDisplayName(i.getDisplayName()))
                .forEach((i) -> {
                    var role = new RoleEntity();
                    role.setDisplayName(i.getDisplayName());

                    roleJPARepository.save(role);

                    log.info("Initial role has created. [role: {}]", i.getDisplayName());
                });

        initialDataConfiguration
                .getUsers()
                .stream()
                .filter((i) -> !userJPARepository.existsByUsername(i.getUsername()))
                .forEach((i) -> {
                    var role = roleJPARepository
                            .findByDisplayName(i.getRole())
                            .get();

                    var user = new UserEntity();
                    user.setFirstName(i.getFirstName());
                    user.setLastName(i.getLastName());
                    user.setUsername(i.getUsername());
                    user.setPassword(passwordEncoder.encode(i.getPassword()));
                    user.setIsAccountEnabled(true);
                    user.setRole(role);

                    userJPARepository.save(user);

                    log.info("Initial user has created. [user: {}]", i.getUsername());
                });
    }
}
