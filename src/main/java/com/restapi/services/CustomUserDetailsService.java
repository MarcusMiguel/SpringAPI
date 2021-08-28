package com.restapi.services;

import com.restapi.repositories.UserRepository;
import com.restapi.models.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.restapi.security.ApplicationUserRole.ADMIN;
import static com.restapi.security.ApplicationUserRole.CONSUMER;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username);

        if ( user == null) throw new UsernameNotFoundException("Username not found");

        UserDetails user2 = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();

        return user2;
    }

    public UserModel findUserModelByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public List<UserModel> findAll(){
        return userRepository.findAll();
    }

    public void register(UserModel user){
        userRepository.save(user);
        log.info("USER REGISTERED: " + user.getUsername());
    }

    public void delete(String username){
        UserModel user = userRepository.findByUsername(username);
        userRepository.delete(user);
        log.info("USER DELETED: " + username);
    }

    public void update(UserModel user){
      userRepository.save(user);
      log.info("USER UPDATED: " + user.getUsername());
    }
    public void startUsers(){
        userRepository.save(
                new UserModel(
                        "admin",
                        passwordEncoder.encode("admin"),
                        true,
                        true,
                        true,
                        true,
                        "123456",
                        ADMIN,
                        null
                )
        );
        userRepository.save(
                new UserModel(
                        "consumer",
                        passwordEncoder.encode("consumer"),
                        true,
                        true,
                        true,
                        true,
                        "12345",
                        CONSUMER,
                        null
                )
        );
    }

}