package com.techreturners.bookmanager.service;

import com.techreturners.bookmanager.exceptions.BookNotFoundException;
import com.techreturners.bookmanager.model.Book;
import com.techreturners.bookmanager.repository.BookManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookManagerServiceImpl implements BookManagerService {

    @Autowired
    BookManagerRepository bookManagerRepository;

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookManagerRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Book insertBook(Book book) {
        return bookManagerRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookManagerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No book present with ID = " + id));
    }

    //User Story 4 - Update Book By Id Solution
    @Override
    public void updateBookById(Long id, Book book) {
        Book retrievedBook = bookManagerRepository.findById(id).orElse(null);

        if (retrievedBook != null) {
            retrievedBook.setTitle(book.getTitle());
            retrievedBook.setDescription(book.getDescription());
            retrievedBook.setAuthor(book.getAuthor());
            retrievedBook.setGenre(book.getGenre());

            bookManagerRepository.save(retrievedBook);
        } else {
            throw new BookNotFoundException("The book with id " + id + " cannot be found");
        }
    }

    @Override
    public void deleteByBookId(Long id) {
        // Only delete it if it's there!
        bookManagerRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book with id " + id + " cannnot be found in the database"));
        bookManagerRepository.deleteById(id);
    }

}
