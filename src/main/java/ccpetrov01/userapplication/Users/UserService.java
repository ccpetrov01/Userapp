package ccpetrov01.userapplication.Users;

import ccpetrov01.userapplication.Security.JwtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Page<UserEntity> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public void addNewUser(UserEntity userEntity) {

        userRepository.save(userEntity);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("User with ID " + id + " doesn't exist.");
        }
        userRepository.deleteById(id);
    }

    public void addManyUsers(List<UserEntity> userEntityList) {
        userRepository.saveAll(userEntityList);
    }


    public void updateUserData(Integer id, String firstname, String lastname,String email) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("User with ID " + id + " doesn't exist.");
        }
        UserEntity user = new UserEntity();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        userRepository.save(user);

    }

    public Page<UserEntity> searchUsers(String term, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (term == null || term.isBlank()) {
            throw new IllegalArgumentException("Search term cannot be empty.");
        }
        return userRepository.searchUsers(term, pageable);
    }

    public LoginViewDto login(LoginDto loginDto) {
        UserEntity user = userRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new LoginViewDto(token);
    }

    public UserEntity register(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        if (userRepository.existsByPhonenumber(user.getPhonenumber())) {
            throw new IllegalArgumentException("Phone number already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}

