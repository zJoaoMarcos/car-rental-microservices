package com.devjonas.indentity.application.usecase;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.devjonas.indentity.application.dto.AuthDTO;
import com.devjonas.indentity.domain.entities.Employee;
import com.devjonas.indentity.infra.exception.InvalidCredentialsException;
import com.devjonas.indentity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class AuthEmployee {

    @Value("${security.service-jwt-secret}")
    private String secretKey;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthDTO request) {

        Employee employee = employeeRepository.findByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);

        var passwordMatches = this.passwordEncoder.matches(request.password(), employee.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withIssuer("jacarrental")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(employee.getId().toString())
                .sign(algorithm);
    }
}
