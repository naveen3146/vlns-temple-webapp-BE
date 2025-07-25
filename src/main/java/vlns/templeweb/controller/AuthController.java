package vlns.templeweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vlns.templeweb.Repositroy.UserRepo;
import vlns.templeweb.dto.AuthRequest;
import vlns.templeweb.dto.AuthResponse;
import vlns.templeweb.model.Users;
import vlns.templeweb.util.JWTUtility;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTUtility jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        logger.info("calling register endpoint {}", request.getUsername());
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        System.out.println(new BCryptPasswordEncoder().encode("admin123"));
        user.setRoles(List.of("ROLE_USER")); // default role

        userRepo.save(user);
        logger.info("saved the user into db{}", user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        logger.info("calling login endpoint {} {}", request.getUsername(), request.getPassword());
        System.out.println(new BCryptPasswordEncoder().encode("admin123"));
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = (UserDetails) auth.getPrincipal();
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtUtil.generateToken(user));
        return ResponseEntity.ok(Collections.singletonMap("token", authResponse));
    }
}
