package com.vijaya.cipherwheelencoderalgo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CipherWheelEncoderAlgoApplicationTests {


    @Test
    void contextLoads() {
        ApplicationContext context = SpringApplication.run(CipherWheelEncoderAlgoApplication.class);
        assertNotNull(context);
    }

}
