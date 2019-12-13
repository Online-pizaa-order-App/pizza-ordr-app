//package Security;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//import java.io.IOException;
//import java.security.Principal;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Controller
//public class SecurityController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/register")
//    public String showRegistrationPage(Model model) {
//        model.addAttribute("user", new User());
//        return "registration";
//    }
//
//    @PostMapping("/register")
//    public String processRegistrationPage(@Valid
//                                          @ModelAttribute("user") User user, BindingResult result,
//                                          Model model) {
//        model.addAttribute("user", user);
//        if (result.hasErrors()) {
//            return "registration";
//        } else {
//            userService.saveUser(user);
//            model.addAttribute("message", "User Account Created");
//        }
//        return "index";
//    }
//
//    @RequestMapping("/")
//    public String index(Model model){
//
//        model.addAttribute("users", userRepository.findAll());
//        return "list";
//    }
//
//
//    @RequestMapping("/login")
//    public String login(){
//        return "login";
//    }
//
//
//
//    @Autowired
//    UserRepository userRepository;
//
//    @RequestMapping("/secure")
//    public String secure(Principal principal, Model model){
//    String username = principal.getName();
//    model.addAttribute("user", userRepository.findByUsername(username));
//        return "secure";
//    }
//
//
//    @RequestMapping("/details/{id}")
//    public String createUser(@PathVariable("id") long id, Model model)
//    {
//        model.addAttribute("user", userRepository.findById(id).get());
//        return "show";
//    }
//
//    @RequestMapping("/update/{id}")
//    public String updateUser(@PathVariable("id") long id, Model model)
//    {
//        model.addAttribute("user", userRepository.findById(id).get());
//        return "userform";
//    }
//
//    @RequestMapping("/delete/{id}")
//    public String delUser(@PathVariable("id") long id){
//        userRepository.deleteById(id);
//        return "redirect:/";
//    }
//}
