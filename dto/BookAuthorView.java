package com.bits.library.dto;

public class BookAuthorView {

    private final Long bookId;
    private final String title;
    private final Integer publishedYear;
    private final String authorName;
    private final String authorEmail;

    public BookAuthorView(Long bookId, String title, Integer publishedYear, String authorName, String authorEmail) {
        this.bookId = bookId;
        this.title = title;
        this.publishedYear = publishedYear;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }
}
