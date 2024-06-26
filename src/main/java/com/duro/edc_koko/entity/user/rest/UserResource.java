package com.duro.edc_koko.entity.user.rest;

import com.duro.edc_koko.entity.user.domain.User;
import com.duro.edc_koko.entity.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(userService.get(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateUser(@RequestBody @Valid final User user) {
        userService.update(user);
        return ResponseEntity.ok(user.getId());
    }


}
