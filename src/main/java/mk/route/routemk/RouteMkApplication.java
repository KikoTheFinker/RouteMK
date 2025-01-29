package mk.route.routemk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RouteMkApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouteMkApplication.class, args);
    }
}
