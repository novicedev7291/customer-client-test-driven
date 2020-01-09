package com.kuldeep.customer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public class CustomerClientConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public CustomerClient customerClient(RestTemplate restTemplate,
                                         @Value("${customer-svc.host.uri:http://localhost:8080}") String uri){
        return new CustomerClient(restTemplate, uri);
    }
}
