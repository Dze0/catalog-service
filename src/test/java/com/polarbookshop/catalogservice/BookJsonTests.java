package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = new Book(123L,"1234567890", "Title", "Author", 9.9,"publisher",
                null,null,0);
        var jsonContent = json.write(book);

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());

    }

    @Test
    void testDeserialize() throws Exception {
        var instant = Instant.parse("2021-09-07T22:50:37.135029Z");
        var content = """
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.9,
                    "publisher": "publisher",
                    "createdDate": "2021-09-07T22:50:37.135029Z",
                    "lastModifiedDate": "2021-09-07T22:50:37.135029Z",
                    "version": 0
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book(123L,"1234567890", "Title", "Author", 9.9,  "publisher",
                        instant,instant,0));
    }
}
