package it.epicode.U4_S6_L5_weekly_project.services;

import it.epicode.U4_S6_L5_weekly_project.config.MailgunSender;
import it.epicode.U4_S6_L5_weekly_project.exceptions.BadRequestException;
import it.epicode.U4_S6_L5_weekly_project.exceptions.NotFoundException;
import it.epicode.U4_S6_L5_weekly_project.entities.User;
import it.epicode.U4_S6_L5_weekly_project.payloads.UserRegisterRequestDTO;
import it.epicode.U4_S6_L5_weekly_project.payloads.UserRegisterResponseDTO;
import it.epicode.U4_S6_L5_weekly_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private MailgunSender mailgunSender;

    // GET all
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // GET id
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // POST
    public UserRegisterResponseDTO saveUser(UserRegisterRequestDTO user) {
        userRepository.findByEmail(user.email()).ifPresent(u -> {
            throw new BadRequestException("Email " + u.getEmail() + " already in use!");
        });

        User newUser = new User(
                user.firstName(),
                user.lastName(),
                user.email(),
                bcrypt.encode(user.password()),
                "https://ui-avatars.com/api/?name=" + user.firstName() + "+" + user.lastName()
        );

        userRepository.save(newUser);
        // mailgunSender.sendRegistrationEmail(newUser);

        return new UserRegisterResponseDTO(newUser.getId());
    }

    // PUT
    public User updateUser(long id, User updatedUser) {
        User userToBeUpdated = this.getUserById(id);
        userToBeUpdated.setFirstName(updatedUser.getFirstName());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setEmail(updatedUser.getEmail());
        userToBeUpdated.setPassword(updatedUser.getPassword());
        userToBeUpdated.setAvatar("https://ui-avatars.com/api/?name=" + updatedUser.getFirstName() + "+" + updatedUser.getLastName());
        userRepository.save(userToBeUpdated);
        return userToBeUpdated;
    }

    // DELETE
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    // FIND USER BY USERNAME
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Item with " + username + " not found."));
    }

    // FIND USER BY EMAIL
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Item with " + email + " not found."));
    }
}
