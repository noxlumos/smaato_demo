package com.example.smaato.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SmaatoController.class)
class SmaatoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAcceptWithoutEndpoint() throws Exception {
        this.mvc.perform(get("/api/smaato/accept?id=123456789")).andDo(print()).andExpect(status().isOk()).andReturn();
    }


    @Test
    public void testAcceptWithoutEndpointWithDuplicateIds() throws Exception {
        PrintWriter writer = new PrintWriter("counterLog.txt");
        writer.close();
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
        for (int i=0; i<100; i++) {
            executor.submit(() -> {
                try {
                    this.mvc.perform(get("/api/smaato/accept?id=123456789")).andDo(print()).andExpect(status().isOk()).andReturn();
                } catch (Exception e) {
                    fail();
                }
            });
        }
        Thread.sleep(1000 * 60);
        List<String> allLines = Files.readAllLines(Paths.get("counterLog.txt"));
        for(String line: allLines) {
            assert(Integer.parseInt(line.trim()) <= 1);
        }
    }

    @Test
    public void testAcceptWithoutEndpointWithUniqueIds() throws Exception {
        PrintWriter writer = new PrintWriter("counterLog.txt");
        writer.close();
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
        for (int i=0; i<100; i++) {
            int finalI = i;
            executor.submit(() -> {
                try {
                    this.mvc.perform(get("/api/smaato/accept?id=" + Integer.toString(finalI))).andDo(print()).andExpect(status().isOk()).andReturn();
                } catch (Exception e) {
                    fail();
                }
            });
        }
        Thread.sleep(1000 * 60);
        List<String> allLines = Files.readAllLines(Paths.get("counterLog.txt"));
        int sum = 0;
        for(String line: allLines) {
            sum += Integer.parseInt(line.trim());
        }
        assert(sum == 100);
    }
}