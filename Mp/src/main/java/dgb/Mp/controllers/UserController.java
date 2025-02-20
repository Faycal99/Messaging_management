package dgb.Mp.controllers;

import dgb.Mp.user.Dtos.UserDtoLogin;
import dgb.Mp.user.User;
import dgb.Mp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController("/cm/v1/users")
public class UserController {

    private final UserService userService;



    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User loging(HttpRequest request, UserDtoLogin userDtoLogin) {



        return null;
    }






}
