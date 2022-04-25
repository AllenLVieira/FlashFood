package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.UserNotFoundException;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        userRepository.detach(user);
        Optional<User> existentUser = userRepository.findByEmail(user.getEmail());
        if (existentUser.isPresent() && !existentUser.get().equals(user)) {
            throw new BusinessException(
                    String.format("There is already a user registration with the email: %s", user.getEmail()));
        }
        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(Long userId, String actualPassword, String newPassword) {
        User user = findUserOrElseThrow(userId);
        if (user.passwordNotConfirmed(actualPassword)) {
            throw new BusinessException("Current password reported does not match the user's password.");
        }
        user.setPassword(newPassword);
    }

    public User findUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
