package com.bjtu.androidbackend;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BCryptTest {

    @Test
    public void testBCrypt() {
        String hashed = BCrypt.withDefaults().hashToString(10, "123456".toCharArray());
        System.out.println(hashed);

        BCrypt.Result result = BCrypt.verifyer().verify("123456".toCharArray(), hashed);
        System.out.println(result.verified);
    }
}
