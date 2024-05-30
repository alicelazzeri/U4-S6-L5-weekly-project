package it.epicode.U4_S6_L5_weekly_project.config;

import it.epicode.U4_S6_L5_weekly_project.entities.User;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;

public class MailgunSender {
    private String apiKey;
    private String domainName;

    public MailgunSender(@Value("{mailgun.apikey}") String apiKey, @Value("{mailgun.domainname}") String domainName) {
        this.apiKey = apiKey;
        this.domainName = domainName;
    }

    public void sendRegistrationEmail(User recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/"+ this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "alice.lazzeri@hotmail.it")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registration was successful!")
                .queryString("text", "Welcome on board " + recipient.getFirstName() + "! Thanks for joining us!")
                .asJson();
        System.out.println(response.getBody());
    }
}
