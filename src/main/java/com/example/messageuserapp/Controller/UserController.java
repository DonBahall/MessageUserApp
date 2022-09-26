package com.example.messageuserapp.Controller;

import com.example.messageuserapp.Model.Roles;
import com.example.messageuserapp.Model.UserModel;
import com.example.messageuserapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/")
    public String main_page(){
        return "welcome_page";
    }

    @GetMapping("/register_page")
    public String get_register(Model model){
        model.addAttribute("Register",new UserModel());
        return "register_page";
    }
    @PostMapping("/register_page")
    public String register( @RequestParam String RegLogin, @RequestParam String RegPassword ) {
    UserModel userModel = new UserModel(RegLogin, RegPassword, Roles.USER,true);
    String password = this.passwordEncoder.encode(userModel.getPassword());
    userModel.setPassword(password);
    userRepository.save(userModel);
      return "redirect:/login";
    }
    @GetMapping("/login")
    public String get_login(Model model){
        model.addAttribute("Login",new UserModel());
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
        UserModel userModel = userRepository.findByUsernameAndPassword(username, password).orElse(null);
        if(userModel != null) return "lobby";
        else return "redirect:/login";
    }
}
