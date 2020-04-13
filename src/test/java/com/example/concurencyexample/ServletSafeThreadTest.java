package com.example.concurencyexample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
class ServletSafeThreadTest {

    @Autowired
    private MockMvc mockMvc;

    private int i;

    @Test
    void testSafeThreadConcurrentServletMethod() throws Exception{

        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < 50000; i++){
            service.submit(() -> {
                try {
                    test();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }
    }

    void test () throws Exception{

        this.mockMvc.perform(get("/example/1?number=0"))
                .andDo(print())
                .andExpect(xpath("//*[@id=\"count\"]").string("\n" +
                        "            total count: " + String.valueOf(++i) + "\n" +
                        "    "));
    }
}