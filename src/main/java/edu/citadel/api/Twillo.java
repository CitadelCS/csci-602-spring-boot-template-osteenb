package edu.citadel.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import com.twilio.type.PhoneNumber;
import java.util.Arrays;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.converter.Promoter;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.math.BigDecimal;

@RestController
@RequestMapping("/")
public class Twillo {


    @GetMapping(value = "text")

    public void text() {

        System.out.println("text sample sent");

        // Find your Account Sid and Token at twilio.com/console
        String ACCOUNT_SID = "AC3e1583af40051646902759e9eba08fc9";
        String AUTH_TOKEN = "10b4326ac22ce394dee336370e5ed29a";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+18434129696"),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        "{text for message}")
                .create();

        System.out.println(message.getSid());
    }
}


