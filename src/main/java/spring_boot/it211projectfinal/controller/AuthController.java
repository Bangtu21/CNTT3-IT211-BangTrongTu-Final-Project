package spring_boot.it211projectfinal.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot.it211projectfinal.model.dto.request.*;
import spring_boot.it211projectfinal.model.dto.response.ApiResponseDTO;
import spring_boot.it211projectfinal.model.dto.response.AuthResponseDTO;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;
import spring_boot.it211projectfinal.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> register(
            @Valid
            @RequestBody
            RegisterRequestDTO request
    ) {
        UserResponseDTO user = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.<UserResponseDTO>builder()
                                .success(true)
                                .message("Register successfully")
                                .data(user)
                                .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(@RequestBody LoginRequestDTO request){
        return ResponseEntity.ok(ApiResponseDTO.<AuthResponseDTO>builder()
                .success(true)
                .message("Login successfully")
                .data(authService.login(request))
                .build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> refreshToken(@RequestBody RefreshTokenRequestDTO request){
        return ResponseEntity.ok(ApiResponseDTO
                .<AuthResponseDTO>builder()
                .success(true)
                .message("Refresh token success")
                .data(authService.refreshToken(request))
                .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO<Void>> logout(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);

        authService.logout(token);

        return ResponseEntity.ok(ApiResponseDTO
                .<Void>builder()
                .success(true)
                .message("Logout successfully").build()
        );
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponseDTO<Void>> changePassword(@RequestBody ChangePasswordRequestDTO request){
        authService.changePassword(request);

        return ResponseEntity.ok(ApiResponseDTO
                .<Void>builder()
                .success(true)
                .message("Password changed successfully")
                .build()
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponseDTO<String>> forgotPassword(@RequestBody ForgotPasswordRequestDTO request){
        String token = authService.forgotPassword(request);

        return ResponseEntity.ok(ApiResponseDTO
                .<String>builder()
                .success(true)
                .message("Reset token generated")
                .data(token)
                .build()
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDTO<Void>> resetPassword(@RequestBody ResetPasswordRequestDTO request){
        authService.resetPassword(request);

        return ResponseEntity.ok(ApiResponseDTO
                .<Void>builder()
                .success(true)
                .message("Password reset successfully")
                .build()
        );
    }
}
