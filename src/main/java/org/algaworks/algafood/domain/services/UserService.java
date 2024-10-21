package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.EntityInUseException;
import org.algaworks.algafood.domain.exceptions.UserNotFoundException;
import org.algaworks.algafood.domain.models.Group;
import org.algaworks.algafood.domain.models.User;
import org.algaworks.algafood.domain.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private GroupService groupService;
    private PasswordEncoder bcrypt;
    private static final String MSG_IN_USER = "The email %s is already in use.";

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User add(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            String passwordHash = bcrypt.encode(user.getPassword());
            user.setPassword(passwordHash);
            return userRepository.save(user);
        }
        throw new BusinessException(String.format(MSG_IN_USER, user.getEmail()));
    }

    @Transactional
    public User update(User user) {
        Optional<User> optionalUserByEmail = userRepository.findByEmail(user.getEmail());
        if (optionalUserByEmail.isPresent() && optionalUserByEmail.get().equals(user) || optionalUserByEmail.isEmpty()) {
            return userRepository.saveAndFlush(user);
        }
        throw new BusinessException(String.format(MSG_IN_USER, user.getEmail()));
    }

    @Transactional
    public void updatePassword(Long userId, String currentPassword, String newPassword) {
        User user = findById(userId);
        if (!bcrypt.matches(currentPassword, user.getPassword())) {
            throw new BusinessException("The current password entered does not match the user's password.");
        }
        user.setPassword(bcrypt.encode(newPassword));
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            this.findById(id);
            userRepository.deleteById(id);
            userRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(e.getMessage());
        }
    }

    @Transactional
    public Set<Group> findAllGroups(Long userId) {
        User user = this.findById(userId);
        Hibernate.initialize(user.getGroups());
        return user.getGroups();
    }

    @Transactional
    public void attachGroup(Long userId, Long groupId) {
        User user = this.findById(userId);
        Group group = groupService.findById(groupId);
        user.attachGroup(group);
    }

    @Transactional
    public void detachGroup(Long userId, Long groupId) {
        User user = this.findById(userId);
        Group group = groupService.findById(groupId);
        user.detachGroup(group);
    }
}
