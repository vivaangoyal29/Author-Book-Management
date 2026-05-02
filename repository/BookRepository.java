package com.bits.library.repository;

import com.bits.library.dto.BookAuthorView;
import com.bits.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
            select new com.bits.library.dto.BookAuthorView(
                b.id, b.title, b.publishedYear, a.name, a.email
            )
            from Book b inner join b.author a
            order by b.id
            """)
    List<BookAuthorView> findAllBooksWithAuthorDetails();
}
