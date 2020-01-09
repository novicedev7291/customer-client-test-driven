package com.kuldeep.customer.client;

import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@SpringBootTest(classes = CustomerClientConfiguration.class)
@RunWith(SpringRunner.class)
public class CustomerClientRestServiceServerTest {
    private MockRestServiceServer mockRestServiceServer;

    private Resource customers = new ClassPathResource("customers.json");
    private Resource customerById = new ClassPathResource("customer-by-id.json");

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerClient client;

    @Before
    public void setup(){
        this.mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void shouldReturnAllCustomers(){
        this.mockRestServiceServer.expect(ExpectedCount.manyTimes(), requestTo("http://localhost:8080/customers"))
                .andExpect( method( HttpMethod.GET ))
                .andRespond( withSuccess(customers, MediaType.APPLICATION_JSON) );

        Collection<Customer> customers =  client.getAllCustomers();
        BDDAssertions.then(customers.size()).isEqualTo(2);
        this.mockRestServiceServer.verify();
    }

    @Test
    public void shouldReturnCustomerById (){
        this.mockRestServiceServer.expect(ExpectedCount.manyTimes(), requestTo("http://localhost:8080/customers/1"))
                .andExpect( method( HttpMethod.GET ))
                .andRespond( withSuccess(customerById, MediaType.APPLICATION_JSON) );

        Customer customer = client.getCustomerById(1L);
        BDDAssertions.then(customer.getId()).isEqualTo(1L);
        BDDAssertions.then(customer.getFirst()).isEqualToIgnoringCase("first");
        BDDAssertions.then(customer.getLast()).isEqualToIgnoringCase("last");
        BDDAssertions.then(customer.getEmail()).isEqualTo("first@email.com");
        this.mockRestServiceServer.verify();
    }
}
