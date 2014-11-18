package com.epam.brest.courses.web;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by andrew on 10.11.14.
 */
@Controller
@RequestMapping("/mvc")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/"})
    public ModelAndView launchInputForm() {

        return new ModelAndView("inputForm", "user", new User());
    }

    @RequestMapping(value = {"/submitData"})
    public ModelAndView getInputForm(@RequestParam("login")String login, @RequestParam("name")String userName) {

        User user = new User();
        user.setLogin(login);
        user.setName(userName);
        Long id = userService.addUser(user);
        user.setUserId(id);
        return new ModelAndView("displayAddUserResult", "user", user);
    }

    @RequestMapping(value = {"/getAllUsers"})
    public ModelAndView getAllUsersForm() {

        List<User> userList = userService.getUsers();
        return new ModelAndView("displayAllUsers", "userList", userList);
    }
}