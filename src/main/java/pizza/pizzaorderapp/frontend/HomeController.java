package pizza.pizzaorderapp.frontend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pizza.pizzaorderapp.Security.UserRepository;

import javax.jws.WebParam;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
   public PizzaRepository pizzaRepository;
    
    @Autowired
   public InputRepository inputRepository;

    @Autowired
    UserRepository userRepository;

    Input newinput=new Input();
    public double totalPrice=0;

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("pizzas",pizzaRepository.findAll());
        model.addAttribute("inputs",inputRepository.findAll());
        return "index";
    }


    ///Customized pizza processing steps


    @RequestMapping (value="/orderform", method={RequestMethod.POST,RequestMethod.GET})
    public String topizzaform(Model model, @ModelAttribute Input input ){
        model.addAttribute("pizza",new Pizza());
        Date date=new Date();
        newinput=input;
        newinput.setOrderDate(date);

        model.addAttribute("inputs",inputRepository.findAll());
        return "orderform";
    }

    @RequestMapping (value="/orderform2", method={RequestMethod.POST,RequestMethod.GET})
    public String topizzaform2(Model model){
        model.addAttribute("pizza",new Pizza());

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
//        pizza.setOrdId(newinput.getOrderId());



        pizzas.add(pizza);

//        totalPrice+=price;
        newinput.setPizzaSet(pizzas);

//        newinput.setOrderPrice(totalPrice);
        inputRepository.save(newinput);
        System.out.println(newinput.pizzaSet.get(0).getMeat());

        pizza.setInput(newinput);
        pizzaRepository.save(pizza);
        return "form2";
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
        return "pizzadetail";
    }


    @RequestMapping (value="/pizzadetail2", method={RequestMethod.POST,RequestMethod.GET})
    public String topizzadeatail2(Model model,@RequestParam("pizzaName") String meat ){
        model.addAttribute("pizza",new Pizza());
        model.addAttribute("pi",pizzaRepository.findByMeatIgnoreCase(meat));


        return "pizzadetail";
    }

    @PostMapping("/secondprocess")
    public String predifinedpizza(@RequestParam("pizzaid") long id, Model model){
       model.addAttribute("input", new Input());

        Pizza pizza=pizzaRepository.findByPizzaId(id);
//        pizza.setOrdId(newinput.getOrderId());



        List<Pizza> pizzas;
        if(newinput.pizzaSet != null){
            pizzas= new ArrayList<>(newinput.pizzaSet);
        }
        else{

            pizzas = new ArrayList<>();
        }

        pizzas.add(pizza);

//        totalPrice+=pizza.getPrice();
        newinput.setPizzaSet(pizzas);
//        newinput.setOrderPrice(totalPrice);
        inputRepository.save(newinput);
        System.out.println(newinput.pizzaSet.get(0).getSize());
        pizza.setInput(newinput);
        pizzaRepository.save(pizza);
        return "form2";
    }


    // Displaying shopping cart

    @GetMapping("/cart")
    public String shoppingcart(Model model){
        model.addAttribute("inputs",inputRepository.findAll());
        model.addAttribute("pizzas",pizzaRepository.findAll());
        double tax=newinput.getOrderPrice()*0.6;
        model.addAttribute("tax",tax);
        double total=totalPrice+tax;
        model.addAttribute("total",total);

        Iterable<Input> inputs=inputRepository.findAll();
        for(Input input:inputs){
            double price=0;
            for(Pizza pizza:input.pizzaSet){
                price+=pizza.getPrice();
            }
            input.setOrderPrice(price);
        }


        return "index";
    }


    // checkout out

    @GetMapping("/checkout")
    public String checkout(Model model){



        return "checkout";
    }
}
