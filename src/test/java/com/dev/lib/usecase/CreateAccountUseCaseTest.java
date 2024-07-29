//package com.dev.lib.usecase;
//
//import com.dev.notification.app.user.client.api.domain.entity.Account;
//import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static com.dev.lib.enums.MessagePropertiesEnum.TO_EQUALS;
//import static com.dev.lib.enums.MessagePropertiesEnum.TO_NOT_NULL;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CreateAccountUseCaseTest {
//    static final String firstName = "Test";
//    static final String lastName = "Test";
//    static final String email = "email@test.com";
//    static final String password = "Password@";
//    static final boolean isNotAdmin = false;
//
//    static final String EXPECTED_ERROR_MESSAGE_ALREADY_EXISTS = MessageErrorEnum.buildMessageWithParameters(OBJECT_ALREADY_EXISTS_EXCEPTION.code(), "This account with email", email);
//
//    @InjectMocks
//    CreateAccountUseCase useCase;
//
//    @Mock
//    AccountGateway accountGateway;
//
//    @Mock
//    QueueProducer queueProducer;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("Must create account with successfully!")
//    void shouldExecuteUseCaseWithSuccessfully(){
//        // Given
//        final var input = new AccountCreateRequest(firstName, lastName, email, password);
//        when(accountGateway.findAccountByEmailOrNull(email)).thenReturn(null);
//        doNothing().when(accountGateway).saveToAccount(any(), anyString());
//        doNothing().when(queueProducer).sendMessage(any(), any());
//
//        // When
//        final var result = useCase.execute(input);
//
//        // Then
//        assertNotNull(result, TO_NOT_NULL.getMessage());
//        verify(accountGateway, times(1)).findAccountByEmailOrNull(any());
//        verify(queueProducer, times(1)).sendMessage(any(), any());
//        verify(accountGateway, times(1)).saveToAccount(any(), anyString());
//    }
//
//    @Test
//    @DisplayName("Should throws exception when the account already exists.")
//    void shouldThrowsExceptionWhenAccountAlreadyExists(){
//        // Given
//        final var input = new AccountCreateRequest(firstName, lastName, email, password);
//        final var account = Account.create(firstName, lastName, email, password, isNotAdmin);
//        when(accountGateway.findAccountByEmailOrNull(email)).thenReturn(account);
//        doNothing().when(accountGateway).saveToAccount(any(), anyString());
//        doNothing().when(queueProducer).sendMessage(any(), any());
//
//        // When
//        final var exception = assertThrows(InvalidArgumentException.class, () ->
//                useCase.execute(input)
//        );
//
//        // Then
//        assertNotNull(exception, TO_NOT_NULL.getMessage());
//        assertEquals(EXPECTED_ERROR_MESSAGE_ALREADY_EXISTS, exception.getMessage(), TO_EQUALS.getMessage());
//        verify(accountGateway, times(1)).findAccountByEmailOrNull(any());
//        verify(queueProducer, times(0)).sendMessage(any(), any());
//        verify(accountGateway, times(0)).saveToAccount(any(), anyString());
//    }
//}
