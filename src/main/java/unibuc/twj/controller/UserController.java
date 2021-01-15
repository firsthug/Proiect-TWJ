package unibuc.twj.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibuc.twj.dto.UserDTO;
import unibuc.twj.model.User;
import unibuc.twj.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserDTO user) {
        try {
            return ResponseEntity.ok(userService.userLogin(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare login: " + e.getMessage());
        }
    }


    @PostMapping("/signup")
    public ResponseEntity signupUser(@Valid @RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.userSignup(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare signup: " + e.getMessage());
        }
    }

}
