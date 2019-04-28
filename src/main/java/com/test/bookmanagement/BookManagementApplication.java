package com.test.bookmanagement;

import com.test.bookmanagement.model.Book;
import com.test.bookmanagement.model.Comment;
import com.test.bookmanagement.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class BookManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner initBooks (BookRepository repository) {
        return (args) -> {
            repository.save(new Book("Effective Java", "Joshua Bloch", "978-0134685991",new Comment("Very good book",new Date()),new Comment("Very good book",new Date())));
            repository.save(new Book("Java 9 for Programmers", "Harvey Deitel", "978-013477 7566"));
            repository.save(new Book("Core Java SE 9", "Cay S. Horstmann", "978-0134694726"));
        };
    }


}
