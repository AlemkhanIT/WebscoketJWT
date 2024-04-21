package com.alemkhan.webscoketdemo.auth;

import com.alemkhan.webscoketdemo.jwt.JwtTokenUtil;
import com.alemkhan.webscoketdemo.user.User;
import com.alemkhan.webscoketdemo.user.UserDetailsServiceImpl;
import com.alemkhan.webscoketdemo.user.UserRole;
import com.alemkhan.webscoketdemo.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<AuthRespDto> login(@RequestBody AuthReqDto authReqDto) {
        log.info("Login request received for user: {}", authReqDto.username());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReqDto.username(), authReqDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetails userDetails = userDetailsService.loadUserByUsername(authReqDto.username());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(AuthRespDto.builder().token(token).username(userDetails.getUsername()).build());
    }

    @PostMapping("/register")
    public ResponseEntity<AuthRespDto> register(@RequestBody AuthReqDto registerReqDto) {
        log.info("Registration request received for user: {}", registerReqDto.username());

        try {
            userService.registerUser(registerReqDto.username(), registerReqDto.username(), registerReqDto.password());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(AuthRespDto.builder().message(e.getMessage()).build());
        }
    }
}
