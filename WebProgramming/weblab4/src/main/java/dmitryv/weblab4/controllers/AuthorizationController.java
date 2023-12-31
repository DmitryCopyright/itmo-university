package dmitryv.weblab4.controllers;

import dmitryv.weblab4.security.jwt.JwtUtils;
import dmitryv.weblab4.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import dmitryv.weblab4.DTO.JwtDTO;
import dmitryv.weblab4.DTO.UserDTO;
import dmitryv.weblab4.entities.User;

@RestController
@RequestMapping("/web-lab4/api/auth")
public class AuthorizationController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    ResponseEntity<?> get(){
        return ResponseEntity.ok().body("getgetget");
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@Validated @RequestBody UserDTO userDTO) {
        try {
            User user = (User) userService.loadUserByUsername(userDTO.getUsername());
            if (user == null) {
                throw new IllegalArgumentException();
            } else if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                throw new IllegalAccessException();
            } else {
                String jwt = jwtUtils.generateJwtToken(userDTO.getUsername());
                return ResponseEntity.ok(new JwtDTO(userDTO.getUsername(), jwt));
            }
        } catch (IllegalArgumentException | IllegalAccessException exception) {
            return ResponseEntity.badRequest().body("Неверное имя пользователя или пароль.");
        }
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@Validated @RequestBody UserDTO userDTO) {
        try {
            if (userService.loadUserByUsername(userDTO.getUsername()) != null) {
                throw new IllegalArgumentException();
            }
            userService.addUser(new User(
                    userDTO.getUsername(),
                    passwordEncoder.encode(userDTO.getPassword())
            ));
            return ResponseEntity.ok().body(userDTO.getUsername());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body("Имя пользователя '" + userDTO.getUsername() + "' уже используется.");
        }
    }
}
