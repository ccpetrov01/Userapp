package ccpetrov01.userapplication.Users;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
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
}
