package com.lhind.controller;

import com.lhind.dto.user.UpdatePasswordDto;
import com.lhind.dto.user.UserDto;
import com.lhind.dto.user.UserResponseDto;
import com.lhind.dto.user.UserUpdateDto;
import com.lhind.security.UserPrincipal;
import com.lhind.service.IUserService;
import com.lhind.util.AuthenticationFacade;
import com.lhind.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = Paths.USERS)
public class UserController {

    @Autowired
    private IUserService userService;

    private final AuthenticationFacade authenticationFacade;

    public UserController(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/admin/insertUser")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> insertUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.insertUser(userDto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserResponseDto> getAll(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                        @RequestParam(value = "field", defaultValue = "id") String field
    ) {
        return userService.getAllUsersSortedByField(pageNumber, pageSize, field);
    }

    @PutMapping(Paths.BY_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Integer id, @Valid @RequestBody UserUpdateDto userToBeUpdated) {
        return ResponseEntity.ok(userService.updateUser(id, userToBeUpdated));
    }

    @DeleteMapping(Paths.BY_ID)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(Paths.BY_ID)
    @PreAuthorize("hasAuthority('SUPERVISOR','ADMIN')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));

    }


    @PostMapping(Paths.CHANGE_PASSWORD)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'USER', 'ADMIN')")
    public ResponseEntity<Void> changePassword(@RequestBody UpdatePasswordDto changePasswordDto) {
        UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
        userService.changePassword(changePasswordDto, userPrincipal.getId());
        return ResponseEntity.ok().build();
    }
}
