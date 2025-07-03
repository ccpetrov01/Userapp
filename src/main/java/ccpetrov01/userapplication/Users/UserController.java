package ccpetrov01.userapplication.Users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Operation(summary = "Add a new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully added"),
            @ApiResponse(responseCode = "400", description = "Validation failed or bad request")
    })
    @PostMapping("/add")
    public void addNewUser(@RequestBody @Valid UserEntityDto userEntityDto)
    {
        UserEntity userEntity = userMapper.toUserEntityDto(userEntityDto);
        userService.addNewUser(userEntity);
    }

    @Operation(summary = "Adding a new Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully added"),
            @ApiResponse(responseCode = "400", description = "Validation failed or bad request")
    })
    @PostMapping("/addAll")
    public void addNewUsers(@RequestBody @Valid List<UserEntityDto> userEntityDtoList)
    {
        List<UserEntity> userEntities = userMapper.toUserDetailsDtolist(userEntityDtoList);
        userService.addManyUsers(userEntities);
    }

    @Operation(summary = "Get information of all Users")
    @GetMapping("/getUsers")
    public Page<UserEntityViewDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return userService.getAllUsers(page, size)
                .map(userMapper::toUserEntityViewDto);

    }

    @Operation(summary = "Delete User by ID")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @Valid Integer id){
        userService.deleteUser(id);
    }
    @Operation(summary = "Updating firstname and lastname of user by ID")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserData(
            @PathVariable Integer id,
            @RequestBody @Valid UserEntity request)
    {
        userService.updateUserData(id, request.getFirstname(),
                request.getLastname() ,
                request.getEmail());
        return ResponseEntity.ok("User updated successfully.");
    }

    @Operation(summary = "Get information of all Users by any criteria")
    @GetMapping("/searchUsers")
    public Page<UserEntityViewDto> searchUsers(
            @RequestParam String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return userService.searchUsers(term, page, size)
                .map(userMapper::toUserEntityViewDto);
    }

}
