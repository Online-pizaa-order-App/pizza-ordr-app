package pizza.pizzaorderapp.frontend;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    public static final String ACCOUNT_SID = "ACdd440d2a549012837fef4bfaec829898";
    public static final String AUTH_TOKEN = "ca4e490292facd18b8e47ee9cb03c2d6";

    public static void SmsSend() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber("+19177144000"), // to
                        new PhoneNumber("+12069658039"), // from
                        "Your order is on its way!")
                .create();

        System.out.println(message.getSid());
    }
}
