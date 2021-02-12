package com.epam.izh.rd.online.repository;

import com.epam.izh.rd.online.entity.Author;

import javax.lang.model.type.ArrayType;
import java.util.Arrays;

public class SimpleAuthorRepository implements AuthorRepository {
    private Author [] authors = new Author[0];

    @Override
    public boolean save(Author author) {
        if (findByFullName(author.getName(), author.getLastName()) == null) {
            authors = Arrays.copyOf(authors, authors.length + 1);
            authors[authors.length-1] = author;
            return true;
        }
        else
            return false;

    }

    @Override
    public Author findByFullName(String name, String lastname) {
        for (Author author: authors) {
            if (author.getName().equals(name) &&
                author.getLastName().equals(lastname))
                return author;
        }
        return null;
    }

    @Override
    public boolean remove(Author author) {
        for (int i = 0; i < authors.length; i++) {
            if (author.getName().equals(authors[i].getName()) &&
                author.getLastName().equals(authors[i].getLastName())) {
                //удаление происходит перестановкой последнего автора в массиве
                //на место найденного автора и копированием массива без последнего элемента
                authors[i] = authors[authors.length-1];
                authors = Arrays.copyOf(authors, authors.length-1);
                return true;
            }
        }
        return false;
    }

    @Override
    public int count() {
        return authors.length;
    }
}
