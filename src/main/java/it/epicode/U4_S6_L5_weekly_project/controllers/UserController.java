package it.epicode.U4_S6_L5_weekly_project.controllers;

import it.epicode.U4_S6_L5_weekly_project.entities.User;
import it.epicode.U4_S6_L5_weekly_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")

public class UserController {

    @Autowired
    private UserService userService;

    // converti tutto in response entity!

    // GET all

    @GetMapping
    public Page<User> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    // GET profile

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User authenticatedUser) {
        return authenticatedUser;
    }

    // PUT profile

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User authenticatedUser, @RequestBody User updatedUser) {
        return userService.updateUser(authenticatedUser.getId(), updatedUser);
    }

    // DELETE profile

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User authenticatedUser){
        userService.deleteUser(authenticatedUser.getId());
    }


    @GetMapping("/{id}")
    public User findProfileById(@PathVariable long id){
        return this.userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateProfile(@PathVariable long id, @RequestBody User profilePayload){
        return userService.updateUser(id,  profilePayload);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable long id){
        userService.deleteUser(id);
    }


}
