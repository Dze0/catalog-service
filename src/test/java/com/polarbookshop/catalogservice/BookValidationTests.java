package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp(){

        var factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds(){
        var book = new Book(123L,"1234567890", "Title", "Author", 9.90,"publisher",
                null,null,0);
        var violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefinedBuIncorrectThenValidationFails(){
        var book = new Book(123L, "a123123123123", "Title", "Author", 9.90, "publisher",null,null,0);
        var violations = validator.validate(book);
        assertThat(violations).hasSize(1);
    }
}
