package com.kuldeep.customer.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */

@SpringBootTest(classes = CustomerClientConfiguration.class)
@RunWith(SpringRunner.class)
@AutoConfigureWireMock
public class CustomerClientRestServiceServerWithWireMockTest {
    private Resource customers = new ClassPathResource("customers.json");
    private Resource customerById = new ClassPathResource("customer-by-id.json");

    @Autowired
    private CustomerClient client;

    @Test
    public void shouldReturnAllCustomers(){
        WireMock.stubFor( WireMock.get( WireMock.urlMatching( "/customers") )
            .willReturn(
                    WireMock.aResponse()
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody( asJson(customers) )
            )
        );
        Collection<Customer> customers =  client.getAllCustomers();
        BDDAssertions.then(customers.size()).isEqualTo(2);
    }

    private String asJson(Resource resource){
        try(BufferedReader bfr = new BufferedReader(new InputStreamReader(resource.getInputStream()))){
            Stream<String> lines = bfr.lines();
            return lines.collect(Collectors.joining());
        }
        catch (Exception e){
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }

    @Test
    public void shouldReturnCustomerById (){
        WireMock.stubFor( WireMock.get( WireMock.urlMatching( "/customers/1") )
                .willReturn(
                        WireMock.aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody( asJson(customerById) )
                )
        );
        Customer customer = client.getCustomerById(1L);
        BDDAssertions.then(customer.getId()).isEqualTo(1L);
        BDDAssertions.then(customer.getFirst()).isEqualToIgnoringCase("first");
        BDDAssertions.then(customer.getLast()).isEqualToIgnoringCase("last");
        BDDAssertions.then(customer.getEmail()).isEqualTo("first@email.com");
    }
}
