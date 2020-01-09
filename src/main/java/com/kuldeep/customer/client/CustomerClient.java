package com.kuldeep.customer.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public class CustomerClient {
    private RestTemplate restTemplate;
    private String uri;

    public CustomerClient(RestTemplate restTemplate, String uri) {
        this.restTemplate = restTemplate;
        this.uri = uri;
    }

    public Collection<Customer> getAllCustomers(){
        ParameterizedTypeReference<Collection<Customer>> ptr = new ParameterizedTypeReference<Collection<Customer>>() {};
        return this.restTemplate.exchange(this.uri + "/customers", HttpMethod.GET, null, ptr).getBody();
    }

    public Customer getCustomerById(Long id) {
        return this.restTemplate.exchange(this.uri + "/customers/"+id, HttpMethod.GET, null, Customer.class).getBody();
    }
}
