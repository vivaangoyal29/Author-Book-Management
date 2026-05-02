package com.bits.library.service;

import com.bits.library.dto.BookAuthorView;
import com.bits.library.entity.Author;
import com.bits.library.entity.Book;
import com.bits.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<BookAuthorView> findAllBookAuthorRows() {
        return bookRepository.findAllBooksWithAuthorDetails();
    }

    public List<BookAuthorView> findBookAuthorRowsByAuthorId(Long authorId) {
        if (authorId == null) {
            return findAllBookAuthorRows();
        }
        return bookRepository.findBooksWithAuthorDetailsByAuthorId(authorId);
    }

    public Book saveBook(Book book, Long authorId) {
        Author author = authorService.findById(authorId);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found for id: " + id));
    }

    public Book updateBook(Long id, String title, Integer publishedYear, Long authorId) {
        Book existing = findById(id);
        Author author = authorService.findById(authorId);
        existing.setTitle(title);
        existing.setPublishedYear(publishedYear);
        existing.setAuthor(author);
        return bookRepository.save(existing);
    }
}
