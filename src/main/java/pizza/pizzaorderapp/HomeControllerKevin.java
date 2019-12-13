package pizza.pizzaorderapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HomeControllerKevin {
    @RequestMapping("/")
    public String landing(Model model) {
        return "landing";
    }

    @RequestMapping("/orderform")
    public String orderForm(Model model) {
        return "orderform";
    }

    @RequestMapping("/checkout")
    public String checkout(Model model) {
        return "checkout";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "loginpage";
    }
}
