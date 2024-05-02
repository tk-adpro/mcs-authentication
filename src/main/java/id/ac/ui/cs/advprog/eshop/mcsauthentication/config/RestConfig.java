package id.ac.ui.cs.advprog.eshop.mcsauthentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
}
