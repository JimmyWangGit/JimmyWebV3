package com.jimmy.jimmyhomepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;


@RestController
@SpringBootApplication
public class PostComment {

    public static void main(String[] args) {
        SpringApplication.run(PostComment.class, args);
    }

    @GetMapping("/highschool")
    public String highschool (){
        return "Highschool";
    }


    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/comment")
    public String comment(@RequestParam(value = "comment", defaultValue = "World") String commentValue) {

        setComment(commentValue);
        return String.format("The following sentence %s! has been inserted into the database", commentValue);

    }

    @Configuration
    public class CorsConfig {

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
                            .allowedHeaders("*");
                }
            };
        }
    }


    public void setComment(String commentValue) {
        try {
            String myDriver = "com.mysql.cj.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/comment";

            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "WAN1384-8");

            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            String query = " insert into comment (date, comment)"
                    + " values (?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDate(1, startDate);
            preparedStmt.setString(2,commentValue);

            preparedStmt.execute();

            conn.close();
        }
                    catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        }

    }

