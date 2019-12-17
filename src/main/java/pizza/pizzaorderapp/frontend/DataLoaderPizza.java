package pizza.pizzaorderapp.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoaderPizza implements CommandLineRunner {

    @Autowired
    PizzaRepository pizzaRepository;


    @Override
    public void run(String... strings) throws Exception{

        Pizza pizza=new Pizza("https://cdn.shopify.com/s/files/1/0808/5563/products/061219_Pepperoni_Full_800x.jpg?v=1560820716",14.50,"large","thin","square","normal","BBQ","light","Peporoni","Onion");

        pizzaRepository.save(pizza);

        pizza=new Pizza("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQol9yz-DsDRlgjFwpGbIk8OmWlcqvc0UpMWlsU2t7N33M8RxMr&s",13.50,"large","thin","square","normal","BBQ","light","Cheese","Cabbage");

        pizzaRepository.save(pizza);

    }

}

