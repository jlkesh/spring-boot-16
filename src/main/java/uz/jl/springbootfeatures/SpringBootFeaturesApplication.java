package uz.jl.springbootfeatures;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.TelegramRequest;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author "Elmurodov Javohir"
 * @since 18/08/22/12:09 (Thursday)
 * spring-boot-features/IntelliJ IDEA
 */
@BotController
@SpringBootApplication
public class SpringBootFeaturesApplication implements TelegramMvcController {

    @Value("${bot.token}")
    private String token;

    @Override
    public String getToken() {
        return token;
    }

    @BotRequest(value = "/hello", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest hello(User user, Chat chat) {
        return new SendMessage(chat.id(), "Hello, " + user.firstName() + "!");
    }

    @MessageRequest("/hello {name:[\\S]+}")
    public String helloWithName(@BotPathVariable("name") String userName) {
        // Return a string if you need to reply with a simple message
        return "Hello, " + userName;
    }

    @MessageRequest("/helloCallback")
    public String helloWithCustomCallback(TelegramRequest request, User user) {
        request.setCallback(new Callback() {
            @Override
            public void onResponse(BaseRequest request, BaseResponse response) {
                // TODO
            }

            @Override
            public void onFailure(BaseRequest request, IOException e) {
                // TODO
            }
        });
        return "Hello, " + user.firstName() + "!";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class);
    }
}

