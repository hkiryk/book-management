package com.test.bookmanagement.service;

import com.test.bookmanagement.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
@Service
public class Validator {

    public void validate(@RequestBody Book car) {
        if (car.getAuthor() == null){
            throw new ResponseStatusException(BAD_REQUEST, "author is null");
        }
        if (car.getTitle() == null){
            throw new ResponseStatusException(BAD_REQUEST, "title is null");
        }
        if (car.getIsbn13() == null){
            throw new ResponseStatusException(BAD_REQUEST, "ISBN-13 is null");
        }
    }


}
