package com.example.usingjwt.api;

import com.example.usingjwt.domain.AppUser;
import com.example.usingjwt.domain.Role;
import com.example.usingjwt.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Log4j2
@RequestMapping("/api/v6")
public class UserControllerResource {

    private final UserService userService;

    public UserControllerResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getAppUsers());

    }

    @PostMapping("/users/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
        return ResponseEntity.created(null).body(userService.saveUser(appUser));

    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));

    }

}
