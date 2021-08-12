package tw.com.housekeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = { "tw.com.demo.controller" })
class HousekeepingApplicationTests {

    // @Test
    // void contextLoads() {
    // }

    public static void main(String[] args) {
        SpringApplication.run(HousekeepingApplicationTests.class, args);
    }
}
