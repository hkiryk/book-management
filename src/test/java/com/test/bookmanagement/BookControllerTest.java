package com.test.bookmanagement;

import com.test.bookmanagement.model.Book;
import com.test.bookmanagement.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    public static final ParameterizedTypeReference<List<Book>> LIST_OF_BOOKS = new ParameterizedTypeReference<List<Book>>() {
    };

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void application_returns_a_list_of_books() {
        ResponseEntity<List<Book>> exchange = restTemplate.exchange("/books", HttpMethod.GET, null, LIST_OF_BOOKS);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        List<Book> books = exchange.getBody();
        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Test
    public void you_can_find_one_book_if_id_exists() {
        ResponseEntity<Book> entity = restTemplate.getForEntity("/books/1", Book.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        Book book = entity.getBody();
        assertNotNull(book);
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals("978-0134685991", book.getIsbn13());
    }

    @Test
    public void you_can_save_a_book() {
        Book newBook = new Book("Java 8 in Action", "Raoul-Gabriel Urma", " 9781617291999");

    }

    @Test
    public void you_can_update_a_book() {
        ResponseEntity<Book> entity = restTemplate.getForEntity("/books/2", Book.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        Book book = entity.getBody();
        assertNotNull(book);
        book.setTitle("HELLO WORLD");
        book.setAuthor("Halyna Kiryk");
        book.setIsbn13("19971010");
        HttpEntity<Book> bookEntity = new HttpEntity<>(book);
        ResponseEntity<Book> exchange = restTemplate.exchange("/books/2", HttpMethod.PUT, bookEntity, Book.class);
        Book updatedBook = exchange.getBody();
        assertNotNull(updatedBook);
        assertEquals("HELLO WORLD", updatedBook.getTitle());
        assertEquals("Halyna Kiryk", updatedBook.getAuthor());
        assertEquals("19971010", updatedBook.getIsbn13());
    }

    @Test
    public void you_can_add_a_comment() {
        ResponseEntity<Book> entity = restTemplate.getForEntity("/books/2", Book.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        Book book = entity.getBody();
        assertNotNull(book);
        Set<Comment> bookComments = book.getComments();
        Comment comment = new Comment("I liked it",new Date());
        bookComments.add(comment);
        HttpEntity<Book> bookEntity = new HttpEntity<>(book);
        ResponseEntity<Book> exchange = restTemplate.exchange("/books/2", HttpMethod.POST, bookEntity, Book.class);
        Book commentedBook = exchange.getBody();
        assertNotNull(commentedBook.getComments());
    }
    @Test
    public void you_can_delete_a_book() {
        ResponseEntity<Book> entity = restTemplate.exchange("/books/3",
                HttpMethod.DELETE, null, Book.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        Book book = entity.getBody();
        assertNull(book);
    }
}
