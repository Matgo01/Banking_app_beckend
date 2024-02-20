package com.example.Banking_app_beckend.service;


import com.example.Banking_app_beckend.auth.AuthenticationRequest;
import com.example.Banking_app_beckend.auth.AuthenticationResponse;
import com.example.Banking_app_beckend.auth.RegisterRequest;
import com.example.Banking_app_beckend.config.JwtService;
import com.example.Banking_app_beckend.model.Role;
import com.example.Banking_app_beckend.model.User;
import com.example.Banking_app_beckend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        var user= User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);

        var jwtToken=jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        // Autenticazione dell'utente utilizzando il gestore di autenticazione di Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Recupero dell'utente dal repository tramite l'email
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        // Generazione di un nuovo token JWT per l'utente autenticato
        var jwtToken = jwtService.generateToken(user);

        // Creazione di una risposta di autenticazione contenente il token JWT
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
