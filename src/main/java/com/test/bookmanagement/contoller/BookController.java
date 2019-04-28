package com.test.bookmanagement.contoller;

import com.test.bookmanagement.model.Book;
import com.test.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/books")


public class BookController
{

    @Autowired
    private BookService bookService;


    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public Book findOne(@PathVariable Long id) {
        return bookService.findOne(id);
    }

    @PostMapping
    public Book save(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("{id}")
    public Book update (@RequestBody Book book, @PathVariable Long id) {
        return bookService.update(book, id);
    }

    // Added this functionality here, tested it with JUnit tests and POSTMAN(it worked),
    // but failed to implement it properly on the front-end side.
    @PostMapping("{id}")
    public Book addComments (@RequestBody String text, @PathVariable Long id) {
        return bookService.addComments(text, id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

}
