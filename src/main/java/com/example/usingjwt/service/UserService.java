package com.example.usingjwt.service;


import com.example.usingjwt.domain.AppUser;
import com.example.usingjwt.domain.Role;
import com.example.usingjwt.repository.RoleRepository;
import com.example.usingjwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Slf4j
@Service
public class UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }


    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }


    public void addRole2user(String username, String roleName) {
        AppUser appUser = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        appUser.getRole().add(role);

    }


    public AppUser getAppUser(String username) {
        return userRepository.findByUsername(username);
    }


    public List<AppUser> getAppUsers() {
        return userRepository.findAll();
    }


}
