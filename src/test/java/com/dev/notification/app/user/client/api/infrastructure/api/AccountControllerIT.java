package com.dev.notification.app.user.client.api.infrastructure.api;

import com.dev.notification.app.user.client.api.annotation.ControllerIT;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.infrastructure.api.controller.AccountController;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.CreateAccountDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.response.AccountSuccessfullyDTO;
import com.dev.notification.app.user.client.api.infrastructure.exception.models.ExceptionResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

@ControllerIT(controllers = AccountController.class)
class AccountControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private AccountGateway accountGateway;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private Gson gson;

    private static final String HOST = "http://localhost:";
    private static final String PATH_CREATE_ACCOUNT = "/api/account/create";

    @Test
    @DisplayName("Should create a new account with success, and return http status: 201.")
    void t1(){
        // Given
        final var dto = CreateAccountDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("SecureP@ssword123")
                .build();
        final var json = gson.toJson(dto);

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final var request = new HttpEntity<>(json, headers);

        // When
        final var response = testRestTemplate.exchange(
                buildUrl(PATH_CREATE_ACCOUNT),
                HttpMethod.POST,
                request,
                AccountSuccessfullyDTO.class
        );

        // Then
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("Should throws a exception (@DomainException) when invalid email, and return http status: 400.")
    void t2(){
        // Given
        final var expectedMessageError = "This email is not valid.";
        final var dto = CreateAccountDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe_example.com")
                .password("SecureP@ssword123")
                .build();
        final var json = gson.toJson(dto);

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final var request = new HttpEntity<>(json, headers);

        // When
        final var response = testRestTemplate.exchange(
                buildUrl(PATH_CREATE_ACCOUNT),
                HttpMethod.POST,
                request,
                ExceptionResponse.class
        );

        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(expectedMessageError, response.getBody().message());
    }

    @Test
    @DisplayName("Should throws a exception (@GatewayException) when account already exists, and return http status: 500.")
    void t3(){
        // Given
        final var account = Account.create("John", "Doe", "john.doe@example.com", "Password@123", false);
        accountGateway.save(account);
        final var expectedMessageError = "This account already exists! With email:";
        final var dto = CreateAccountDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("SecureP@ssword123")
                .build();
        final var json = gson.toJson(dto);

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final var request = new HttpEntity<>(json, headers);

        // When
        final var response = testRestTemplate.exchange(
                buildUrl(PATH_CREATE_ACCOUNT),
                HttpMethod.POST,
                request,
                ExceptionResponse.class
        );

        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getStatusCode().is5xxServerError());
        assertEquals(expectedMessageError, response.getBody().message());
    }

    private String buildUrl(final String path){
        return HOST + port + path;
    }
}
