package com.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AdminApplication.class)
class AdminApplicationTests {

    @Test
    void contextLoads() {
        // Verifies Spring context initializes with all modules, security, and repositories
    }
}
