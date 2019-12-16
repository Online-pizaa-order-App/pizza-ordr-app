package pizza.pizzaorderapp.frontend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pizza.pizzaorderapp.Security.User;
import pizza.pizzaorderapp.Security.UserRepository;
import pizza.pizzaorderapp.Security.UserService;

import javax.validation.Valid;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
   public PizzaRepository pizzaRepository;
    
    @Autowired
   public InputRepository inputRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    Input newinput=new Input();
    Long idOrder;

    //Landing page

    @GetMapping("/")
    public String landing(Model model) {
        model.addAttribute("pizzas",pizzaRepository.findAll());
        model.addAttribute("input",new Input());
        model.addAttribute("inputs",inputRepository.findAll());
        model.addAttribute("inp",newinput);

        return "landing";
    }

//   Report page

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("pizzas",pizzaRepository.findAll());
        model.addAttribute("inputs",inputRepository.findAll());
        return "OrderList";
    }


    ///Customized pizza processing steps


    @RequestMapping (value="/orderform", method={RequestMethod.POST,RequestMethod.GET})
    public String topizzaform(Model model, @ModelAttribute Input input ){
        model.addAttribute("pizza",new Pizza());
        Date date=new Date();
        newinput=input;
        newinput.setOrderDate(date);

        model.addAttribute("inputs",inputRepository.findAll());
        model.addAttribute("inp",newinput);
        return "orderform";
    }

    @RequestMapping (value="/orderform2", method={RequestMethod.POST,RequestMethod.GET})
    public String topizzaform2(Model model){
        model.addAttribute("pizza",new Pizza());
        model.addAttribute("inp",newinput);
        return "orderform";
    }





    @RequestMapping (value="/process", method={RequestMethod.POST,RequestMethod.GET})
    public String test(@ModelAttribute Pizza pizza, Model model){

        double price=0;
        switch (pizza.getSize()){
            case "large":
                price=16.80;
                break;
            case "medium":
                price=14.40;
                break;
            case "small":
                price=12.40;
                break;
        }
        switch (pizza.getCrust()){
            case "thin":
                price +=1.5;
                break;
            case "normal":
                price+=0;
                break;
            case "gluten-free":
                price+=1.25;
                break;
        }
        switch (pizza.getCheese()){
            case "extra":
                price+=2.5;
                break;
            case "normal":
            case "light":
            case "none":
                price+=0;
                break;
        }
        String[] array=pizza.getMeat().split(",");
        if (array.length>=2){

          int extra=  array.length-2;
          price+=(1.50*extra);
        }




        List<Pizza> pizzas;
        if(newinput.pizzaSet != null){
            pizzas= new ArrayList<>(newinput.pizzaSet);
        }
        else{

            pizzas = new ArrayList<>();
        }

        pizza.setPrice(price);


        pizzas.add(pizza);

        newinput.setPizzaSet(pizzas);
        newinput.setOrderPrice(newinput.getOrderPrice()+price);


        pizza.setInput(newinput);


        model.addAttribute("inp",newinput);


        inputRepository.save(newinput);
        pizzaRepository.save(pizza);
        return "orderform2";
    }


    ///Pre-defined pizza processing steps

    @RequestMapping (value="/pizzadetail", method={RequestMethod.POST,RequestMethod.GET})
    public String topizzadeatail(Model model, @ModelAttribute Input input,@RequestParam("pizzaName") String meat ){
        model.addAttribute("pizza",new Pizza());
        model.addAttribute("pi",pizzaRepository.findByMeatIgnoreCase(meat));
        Date date=new Date();
        newinput=input;
        newinput.setOrderDate(date);

        model.addAttribute("inputs",inputRepository.findAll());
        model.addAttribute("inp",newinput);

        return "pizzadetail";
    }


    @RequestMapping (value="/pizzadetail2", method={RequestMethod.POST,RequestMethod.GET})
    public String topizzadeatail2(Model model,@RequestParam("pizzaName") String meat ){
        model.addAttribute("pizza",new Pizza());
        model.addAttribute("pi",pizzaRepository.findByMeatIgnoreCase(meat));

        model.addAttribute("inp",newinput);

        return "pizzadetail";
    }

    @PostMapping("/secondprocess")
    public String predifinedpizza(@RequestParam("pizzaid") long id, Model model){
       model.addAttribute("input", new Input());

        Pizza pizza=pizzaRepository.findByPizzaId(id);



        List<Pizza> pizzas;
        if(newinput.pizzaSet != null){
            pizzas= new ArrayList<>(newinput.pizzaSet);
        }
        else{

            pizzas = new ArrayList<>();
        }

        pizzas.add(pizza);

        newinput.setPizzaSet(pizzas);
        newinput.setOrderPrice(newinput.getOrderPrice()+pizza.getPrice());


        pizza.setInput(newinput);


        model.addAttribute("inp",newinput);

        inputRepository.save(newinput);

        return "orderform2";
    }


    // Displaying shopping cart

    @GetMapping("/cart")
    public String shoppingcart(Model model){



        model.addAttribute("inputs",inputRepository.findAll());
        model.addAttribute("pizzas",pizzaRepository.findAll());




        newinput.setTax(newinput.getOrderPrice()*0.06);
        newinput.setTotalPrice(newinput.getOrderPrice()+newinput.getTax());
        model.addAttribute("inp",newinput);

        inputRepository.save(newinput);
        return "OrderList";
    }

    //Admin page

    @GetMapping("/admin")
    public String adminpage(Model model){



        for(Input input:inputRepository.findAll()){
            double price=0;
            for(Pizza pizza:input.pizzaSet){
                price+=pizza.getPrice();
            }
            input.setOrderPrice(price);
            input.setTax(input.getOrderPrice()*0.06);
            input.setTotalPrice(input.getOrderPrice()+input.getTax());
        }
        model.addAttribute("inputs",inputRepository.findAll());


          return "adminPage";
    }

    // checkout out

    @GetMapping("/checkout")
    public String checkout(Model model){

        model.addAttribute("inp",newinput);
        model.addAttribute("user",new User());
        return "checkout";
    }


    @PostMapping("/checkoutprocess")
    public String checkoutprocess(Model model, @Valid @ModelAttribute("user") User user,
                                  BindingResult result){
        if (result.hasErrors()) { return "registration"; }

        // if the username already exists ::
        if (userService.countByUsername(user.getUsername()) > 0) {
            model.addAttribute("message", "username already exists");
            return "registration";
        }

        // if the email already exists ::
        if (userService.countByEmail(user.getEmail()) > 0) {
            model.addAttribute("message", "this email has already been used to register");
            return "registration";
        }


        //connect and save order in user account

        List<Input> inputs;
        if(user.inputs != null){
            inputs= new ArrayList<>(user.inputs);
        }
        else{

            inputs = new ArrayList<>();
        }

        inputs.add(newinput);

        user.setInputs(inputs);

        newinput.setUser(user);

        // if we get this far, then the username and email are valid, and we can move on...
        userService.saveUser(user);
        model.addAttribute("message", "User Account Created");
        model.addAttribute("user", user);


        return "receipt";
    }
    // base html file input repository attachment

    @GetMapping("/base")
    public String basepage(Model model){
        model.addAttribute("inp",newinput);
        return "base";
    }



    // Admin update, detail and delete orders

    @RequestMapping("/update/{id}")
    public String Update(@PathVariable("id") long id, Model model){
        model.addAttribute("pizza",pizzaRepository.findById(id).get());

        model.addAttribute("inp",newinput);
        return "orderform";
    }
    @RequestMapping("/delete/{id}")
    public String Delete(@PathVariable("id") long id, Model model){
        for(Input input:inputRepository.findAll()){
            input.pizzaSet.removeIf(pizza -> pizza.getPizzaId() == id);
        }
        model.addAttribute("inputs",inputRepository.findAll());
        return "adminPage";
    }


}
