package com.example.messageuserapp.Controller;

import com.example.messageuserapp.Model.Roles;
import com.example.messageuserapp.Model.UserModel;
import com.example.messageuserapp.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @GetMapping("/")
    public String mainPage(){
        return "WelcomePage";
    }
    @GetMapping("/registerPage")
    public String getRegister(Model model){
        model.addAttribute("Register",new UserModel());
        return "registerPage";
    }
    @PostMapping("/registerPage")
    public String register( @RequestParam String RegLogin, @RequestParam String RegPassword ){
    UserModel userModel = new UserModel(RegLogin, RegPassword, Roles.USER,true);
    userRepository.save(userModel);
      return "redirect:/loginPage";
    }
    @GetMapping("/loginPage")
    public String getLogin(Model model){
        model.addAttribute("Login",new UserModel());
        return "loginPage";
    }
    @PostMapping("/loginPage")
    public String Login(@RequestParam String username, @RequestParam String password){
        UserModel userModel = userRepository.findByUsernameAndPassword(username, password).orElse(null);
        if(userModel != null) return "Chat";
        else return "redirect:/loginPage";
    }
    @GetMapping("/Chat")
    public String chat(){
        return "Chat";
    }
}
