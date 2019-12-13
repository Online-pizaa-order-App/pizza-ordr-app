package pizza.pizzaorderapp.frontend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    PizzaRepository pizzaRepository;
    
    @Autowired
   InputRepository inputRepository;

    Input newinput=new Input();
    public double totalPrice=0;

    @GetMapping("/receipt")
    public String index(Model model){
        model.addAttribute("pizzas",pizzaRepository.findAll());
        model.addAttribute("inputs",inputRepository.findAll());
        return "receipt";
    }
    @GetMapping("/frontpage")
    public String frontpage(Model model){
        model.addAttribute("input",new Input());
        return "frontpage";
    }


    @RequestMapping (value="/orderform", method={RequestMethod.POST,RequestMethod.GET})
    public String form(Model model, @ModelAttribute Input input ){
        model.addAttribute("pizza",new Pizza());
        Date date=new Date();
        newinput=input;
        newinput.setOrderDate(date);

        model.addAttribute("inputs",inputRepository.findAll());
        return "orderform";
    }
    @RequestMapping (value="/form2", method={RequestMethod.GET})
    public String form2(Model model ){
        model.addAttribute("pizza",new Pizza());

        return "another";
    }

    @PostMapping("/process")
    public String test(@ModelAttribute Pizza pizza){
        double price=0;
        switch (pizza.getSize()){
            case "Large":
                price=16.80;
                break;
            case "Medium":
                price=14.40;
                break;
            case "Small":
                price=12.40;
                break;
        }
        switch (pizza.getCrust()){
            case "Thin":
                price +=1.5;
                break;
            case "Normal":
                price+=0;
                break;
        }
        switch (pizza.getCheese()){
            case "Extra cheese":
                price+=1.5;
                break;
            case "Parmesan Romano":
                price+=2.5;
                break;
            case "3-Cheese Blend":
                price+=2;
                break;
        }
        String[] array=pizza.getMeat().split(",");
        if (array.length>=2){

          int extra=  array.length-2;
          price+=(1.50*extra);
        }

        pizza.setPrice(price);
        pizza.setOrdId(newinput.getOrderId());
        pizzaRepository.save(pizza);


        List<Pizza> pizzas;
        if(newinput.pizzaSet != null){
            pizzas= new ArrayList<>(newinput.pizzaSet);
        }
        else{

            pizzas = new ArrayList<>();
        }
        pizzas.add(pizza);

        totalPrice+=price;
        newinput.setPizzaSet(pizzas);
        newinput.setOrderPrice(totalPrice);
        inputRepository.save(newinput);
        System.out.println(newinput.pizzaSet.get(0).getSize());
        return "redirect:/receipt";
    }


}
