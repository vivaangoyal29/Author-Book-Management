package com.bits.library.controller;

import com.bits.library.entity.Author;
import com.bits.library.entity.Book;
import com.bits.library.service.AuthorService;
import com.bits.library.service.BookService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LibraryController {

    private final AuthorService authorService;
    private final BookService bookService;

    public LibraryController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/books";
    }

    @GetMapping("/authors/new")
    public String showAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("authors", authorService.findAllAuthors());
        return "author-form";
    }

    @PostMapping("/authors")
    public String createAuthor(@ModelAttribute Author author, RedirectAttributes redirectAttributes) {
        try {
            authorService.saveAuthor(author);
            redirectAttributes.addFlashAttribute("successMessage", "Author created successfully.");
            return "redirect:/authors/new";
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Author email must be unique.");
            return "redirect:/authors/new";
        }
    }

    @GetMapping("/books")
    public String listBooks(@RequestParam(required = false) Long authorId, Model model) {
        model.addAttribute("bookRows", bookService.findBookAuthorRowsByAuthorId(authorId));
        model.addAttribute("authors", authorService.findAllAuthors());
        model.addAttribute("selectedAuthorId", authorId);
        model.addAttribute("book", new Book());
        return "book-list";
    }

    @PostMapping("/books")
    public String createBook(@ModelAttribute Book book,
                             @RequestParam("authorId") Long authorId,
                             RedirectAttributes redirectAttributes) {
        try {
            bookService.saveBook(book, authorId);
            redirectAttributes.addFlashAttribute("successMessage", "Book created successfully.");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Integrity violation while creating book.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to create book: " + ex.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/authors/{id}/edit")
    public String showUpdateAuthorForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        model.addAttribute("authors", authorService.findAllAuthors());
        return "author-form";
    }

    @PostMapping("/authors/{id}")
    public String updateAuthor(@PathVariable Long id,
                               @RequestParam String name,
                               @RequestParam String email,
                               RedirectAttributes redirectAttributes) {
        try {
            Author existing = authorService.findById(id);
            existing.setName(name);
            existing.setEmail(email);
            authorService.saveAuthor(existing);
            redirectAttributes.addFlashAttribute("successMessage", "Author updated successfully.");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Author email must be unique.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to update author: " + ex.getMessage());
        }
        return "redirect:/authors/new";
    }

    @GetMapping("/books/{id}/edit")
    public String showUpdateBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("authors", authorService.findAllAuthors());
        return "book-edit";
    }

    @PostMapping("/books/{id}")
    public String updateBook(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam Integer publishedYear,
                             @RequestParam Long authorId,
                             RedirectAttributes redirectAttributes) {
        try {
            bookService.updateBook(id, title, publishedYear, authorId);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to update book: " + ex.getMessage());
        }
        return "redirect:/books";
    }
}
