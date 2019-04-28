package com.test.bookmanagement.service;

import com.test.bookmanagement.model.Book;
import com.test.bookmanagement.model.Comment;
import com.test.bookmanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private Validator bookValidator;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(this::badRequest);
    }

    public Book save(Book book) {
        bookValidator.validate(book);
        return bookRepository.save(book);
    }

    public Book update(Book book, Long id) {
        bookValidator.validate(book);
        Book dbBook = findOne(id);
        dbBook.setTitle(book.getTitle());
        dbBook.setAuthor(book.getAuthor());
        dbBook.setIsbn13(book.getIsbn13());
        return bookRepository.save(book);
    }
    // Added this functionality here, tested it with JUnit tests and POSTMAN(it worked),
    // but failed to implement it properly on the front-end side.
    public Book addComments (String text, Long id){
        Book commentedBook = findOne(id);
        Set<Comment> bookComments = commentedBook.getComments();
        Comment comment = new Comment(text,new Date());
        bookComments.add(comment);
        commentedBook.setComments(bookComments);
        return bookRepository.save(commentedBook);
    }
    public void delete(Long id) {
        Book dbBook = findOne(id);
        bookRepository.delete(dbBook);
    }

    private ResponseStatusException badRequest() {
        return new ResponseStatusException(BAD_REQUEST, "id doesnt exist");
    }
}
