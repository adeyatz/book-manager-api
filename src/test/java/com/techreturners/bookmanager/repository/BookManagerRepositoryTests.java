package com.techreturners.bookmanager.repository;

import com.techreturners.bookmanager.model.Book;
import com.techreturners.bookmanager.model.Genre;

import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookManagerRepositoryTests {

    @Autowired
    private BookManagerRepository bookManagerRepository;

    @Test
    public void testFindAllBooksReturnsBooks() {

        Book book = new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education);
        bookManagerRepository.save(book);

        Iterable<Book> books = bookManagerRepository.findAll();
        assertThat(books).hasSize(1);

    }

    @Test
    public void testCreatesAndFindBookByIdReturnsBook() {

        Book book = new Book(1L, "Book Two", "This is the description for Book Two", "Person Two", Genre.Fantasy);
        bookManagerRepository.save(book);

        var bookById = bookManagerRepository.findById(book.getId());
        assertThat(bookById).isNotNull();

    }

    @Test
    public void testDeleteBookByIdWithNoMatch() {

        Book book = new Book(1L, "Book Three", "This is the description for Book Three", "Person Three", Genre.Fantasy);
        bookManagerRepository.save(book);

        try {
            bookManagerRepository.deleteById(book.getId() + 1);
            System.out.println("*** This test should cause an exception ***");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    /*****************************************************************************
     *     There is a problem with this method of testing (and the 2 first tests):
     *
     *     This test will PASS if it is run in isolation BUT if you
     *     run this tests as Current File then it fails. This is because
     *     bookManagerRepository is auto generated at the start of ALL the tests
     *     and is not reinstanced at the start of each individual test
     *     The effect of this is that the Autonumber of the ID field persists
     *     from one test to the next so although you can pass in an ID number to the
     *     Book object, this is effectively ignored in the database and replace with
     *     the Autonumber.
     *     So for this test, if it is run on its own it passes, but if it is run
     *     as Current File, then Book Four is actually stored with ID=4, rather than
     *     1L as passed into the Book constructor.
     *
     *     Run the test in isolation, and the console reports: Book Four, 1
     *     Run the test as Current File, and the console reports: Book Four, 4
      */
    public void testDeleteBookByIdWithMatch() {

        Book book = new Book(1L, "Book Four", "This is the description for Book Four", "Person Four", Genre.Fantasy);
        bookManagerRepository.save(book);

        Iterable<Book> books = bookManagerRepository.findAll();
        books.forEach(b -> System.out.println(b.getTitle() + ", " +  b.getId()));

        bookManagerRepository.deleteById(book.getId());
        Iterable<Book> bookz = bookManagerRepository.findAll();
        assertThat(bookz).hasSize(0);
    }

}
