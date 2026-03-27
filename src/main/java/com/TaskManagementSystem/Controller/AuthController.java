package com.TaskManagementSystem.Controller;

import com.TaskManagementSystem.DTO.Request.LoginRequest;
import com.TaskManagementSystem.DTO.Request.UserRequest;
import com.TaskManagementSystem.DTO.Response.LoginResponse;
import com.TaskManagementSystem.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    //SIGNUP
    @PostMapping("/signUp")
    public ResponseEntity<LoginResponse> createUser(@Valid @RequestBody UserRequest request,
                                                    HttpServletResponse response) {

        LoginResponse res = userService.createUser(request);

        if ("USER_ALREADY_EXISTS".equals(res.getMessage())) {
            return ResponseEntity.status(409)
                    .cacheControl(CacheControl.noStore())
                    .body(res);
        }

        //SET COOKIE (if token exists)
        if (res.getToken() != null) {
            ResponseCookie cookie = ResponseCookie.from("token", res.getToken())
                    .httpOnly(true)
                    .secure(true) // ⚠️ false in local (http), true in prod (https)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("None")
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
        }

        return ResponseEntity.status(201)
                .cacheControl(CacheControl.noStore())
                .body(res);
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                               HttpServletResponse response) {

        LoginResponse res = userService.loginUser(request);

        switch (res.getMessage()) {

            case "USER_NOT_FOUND":
                return ResponseEntity.status(404)
                        .cacheControl(CacheControl.noStore())
                        .body(res);

            case "INVALID_CREDENTIALS":
                return ResponseEntity.status(401)
                        .cacheControl(CacheControl.noStore())
                        .body(res);

            default:
                //SET COOKIE
                ResponseCookie cookie = ResponseCookie.from("token", res.getToken())
                        .httpOnly(true)
                        .secure(true) // ⚠️ false in local (http), true in prod (https)
                        .path("/")
                        .maxAge(Duration.ofHours(1))
                        .sameSite("None")
                        .build();

                response.addHeader("Set-Cookie", cookie.toString());

                return ResponseEntity.ok()
                        .cacheControl(CacheControl.noStore())
                        .body(res);
        }
    }

    // ✅ LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true) // ⚠️ false in local (http), true in prod (https)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .body("Logged out successfully");
    }
}