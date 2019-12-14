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

        Pizza pizza=new Pizza();
        pizza.setMeat("Peporoni");
        pizza.setPizzaId(1);
        pizza.setPrice(14.50);
        pizza.setPic("https://cdn.shopify.com/s/files/1/0808/5563/products/061219_Pepperoni_Full_800x.jpg?v=1560820716");

        pizzaRepository.save(pizza);

        pizza=new Pizza();
        pizza.setMeat("Cheese");
        pizza.setPizzaId(2);
        pizza.setPrice(13.5);
        pizza.setPic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQol9yz-DsDRlgjFwpGbIk8OmWlcqvc0UpMWlsU2t7N33M8RxMr&s");

        pizzaRepository.save(pizza);

    }

}

