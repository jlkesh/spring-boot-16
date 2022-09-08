package uz.jl.springbootfeatures;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

}

@ShellComponent
class Calculator {

    @ShellMethod(value = "To add two numbers", key = "sum")
    public int add(int a, int b) {
        return a + b;
    }

    @ShellMethod(value = "To sub two numbers", key = "sub")
    public int sub(int a,
                   @ShellOption(defaultValue = "2") int b) {
        return a - b;
    }
}

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
class User {
    private String username;
    private String password;
    private BigInteger balance;
}

@ShellComponent
class TransferMoney {
    List<User> users = new ArrayList<>(List.of(
            User.builder()
                    .username("john")
                    .password("123")
                    .balance(BigInteger.valueOf(1_000_000))
                    .build(),
            User.builder()
                    .username("max")
                    .password("123")
                    .balance(BigInteger.valueOf(100_000_000))
                    .build()));
    private boolean connected;
    private User sessionUser;


    @ShellMethod(value = "Login", key = "login")
    @ShellMethodAvailability("blockLoginCommand")
    public void login(String username, String password) {
        users.stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst().
                ifPresentOrElse(user -> {
                    sessionUser = user;
                    connected = true;
                    System.out.println("Successfully logged in");
                }, () -> System.out.println("Bad credentials"));
    }

    public Availability blockLoginCommand() {
        return !connected ? Availability.available() :
                Availability.unavailable("You can not perform login command because you already logged in");
    }
   public Availability connected() {
        return connected ? Availability.available() :
                Availability.unavailable("You can not perform this action.\nYou should do login");
    }

    @ShellMethod(value = "Show users info", key = "users_info")
    public void showUserInfos() {
        System.out.println(users);
    }

    @ShellMethod(value = "Transfer money from account to account", key = "transfer")
    @ShellMethodAvailability("connected")
    public boolean transfer(String receiver, BigInteger amount) {

        User receiverUser = users.stream().filter(user -> user.getUsername().equals(receiver)).findFirst()
                .orElseThrow(() -> new RuntimeException("Receiver Not Found"));

        sessionUser.setBalance(sessionUser.getBalance().subtract(amount));
        receiverUser.setBalance(receiverUser.getBalance().add(amount));
        System.out.println("Transfer successfully processed");
        return true;
    }

}


