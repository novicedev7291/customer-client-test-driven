package com.kuldeep.customer.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    Long id;
    String first, last;
    String email;
}
