package pizza.pizzaorderapp.frontend;


import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Input {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long orderId;
    private double orderPrice;
    private String orderStatus;
    private java.util.Date orderDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "input",fetch = FetchType.EAGER)

    public List<Pizza> pizzaSet;

    public Input() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Pizza> getPizzaSet() {
        return pizzaSet;
    }

    public void setPizzaSet(List<Pizza> pizzaSet) {
        this.pizzaSet = pizzaSet;
    }
}