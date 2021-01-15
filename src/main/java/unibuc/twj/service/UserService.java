package unibuc.twj.service;

import org.springframework.stereotype.Service;
import unibuc.twj.dto.UserDTO;
import unibuc.twj.exception.UserException;
import unibuc.twj.model.User;
import unibuc.twj.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String userLogin(UserDTO userDTO)
    {
        return userRepository.loginUser(userDTO);
    }

    public User userSignup(User user)
    {
        Boolean isUsernameUnique = userRepository.checkUsernameUnique(user.getUsername());
        if(!isUsernameUnique)
            throw new UserException("Username-ul nu este unic!");
        Boolean isEmailUnique = userRepository.checkEmailUnique(user.getEmail());
        if(!isEmailUnique)
            throw new UserException("Email-ul nu este unic!");

        user.setUserId(userRepository.signupUser(user));
        return user;
    }
}
