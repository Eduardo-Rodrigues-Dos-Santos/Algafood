package org.algaworks.algafood.unit.domain.services;

import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.models.User;
import org.algaworks.algafood.domain.repositories.UserRepository;
import org.algaworks.algafood.domain.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("João Santos");
        user.setEmail("JoãoSantos@email.com");
        user.setPassword("senha123");
        user.setRegistrationDate(LocalDateTime.now());

        user1 = new User();
        user1.setId(2L);
        user1.setName("João Santos Sousa");
        user1.setEmail("JoãoSantos@email.com");
        user1.setPassword("senha123");
        user1.setRegistrationDate(LocalDateTime.now());

        user2 = new User();
        user2.setId(1L);
        user2.setName("João Santos Martins");
        user2.setEmail("JoãoSantos@email.com");
        user2.setPassword("senha123");
        user2.setRegistrationDate(LocalDateTime.now());
    }

    @Test
    void shouldReturnANewUserIfTheEmailIsNotInUse() {
        Mockito.when(userRepository.findByEmail("JoãoSantos@email.com")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode("senha123")).thenReturn("senha123");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assertions.assertDoesNotThrow(() -> userService.add(user) );
    }

    @Test
    void shouldThrowBusinessExceptionIfEmailIsInUse() {
        Mockito.when(userRepository.findByEmail("JoãoSantos@email.com")).thenReturn(Optional.of(user));
        Assertions.assertThrows(BusinessException.class, () -> userService.add(user));
    }

    @Test
    void shouldThrowBusinessExceptionIfUsersIsNotTheSame() {
        Mockito.when(userRepository.findByEmail("JoãoSantos@email.com")).thenReturn(Optional.of(user));
        Assertions.assertThrows(BusinessException.class, () -> userService.update(user1));
    }

    @Test
    void shouldReturnUpdatedUserIfUsersAreTheSame(){
        Mockito.when(userRepository.findByEmail("JoãoSantos@email.com")).thenReturn(Optional.of(user));
        Mockito.when(userRepository.saveAndFlush(user2)).thenReturn(user2);
        userService.update(user2);
        Assertions.assertEquals(user, user2);
    }
}
