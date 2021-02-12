package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Author;
import com.epam.izh.rd.online.entity.Book;
import com.epam.izh.rd.online.entity.SchoolBook;
import com.epam.izh.rd.online.repository.BookRepository;
import com.epam.izh.rd.online.service.AuthorService;

import javax.naming.ldap.LdapReferralException;
import java.util.Arrays;

public class SimpleSchoolBookService implements BookService {
    private BookRepository<SchoolBook> schoolBookBookRepository;
    private AuthorService authorService;

    public SimpleSchoolBookService() {}

    public SimpleSchoolBookService(BookRepository<SchoolBook> schoolBookBookRepository, AuthorService authorService) {
        this.schoolBookBookRepository = schoolBookBookRepository;
        this.authorService = authorService;
    }

    @Override
    public boolean save(Book book) {
        SchoolBook schoolBook = (SchoolBook) book;
        if (authorService.findByFullName(schoolBook.getAuthorName(), schoolBook.getAuthorLastName()) != null) {
            schoolBookBookRepository.save((SchoolBook) book);
            return true;
        }
        else
            return false;
    }

    @Override
    public Book[] findByName(String name) {
        return schoolBookBookRepository.findByName(name);
    }

    @Override
    public int getNumberOfBooksByName(String name) {
        return schoolBookBookRepository.findByName(name).length;
    }

    @Override
    public boolean removeByName(String name) {
        return schoolBookBookRepository.removeByName(name);
    }

    @Override
    public int count() {
        return schoolBookBookRepository.count();
    }

    @Override
    public Author findAuthorByBookName(String name) {
        SchoolBook[] schoolBooks = schoolBookBookRepository.findByName(name);
        if (schoolBooks.length == 0) return null;
        for (SchoolBook book: schoolBooks) {
            return authorService.findByFullName(book.getAuthorName(), book.getAuthorLastName());
        }
        return null;
    }
}
