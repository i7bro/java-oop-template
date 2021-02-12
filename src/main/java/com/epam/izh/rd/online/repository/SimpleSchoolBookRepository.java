package com.epam.izh.rd.online.repository;

import com.epam.izh.rd.online.entity.Book;
import com.epam.izh.rd.online.entity.SchoolBook;
import sun.security.x509.EDIPartyName;

import java.util.Arrays;

public class SimpleSchoolBookRepository implements BookRepository<SchoolBook> {
    private SchoolBook [] schoolBooks = new SchoolBook[0];

    @Override
    public boolean save(SchoolBook book) {
        schoolBooks = Arrays.copyOf(schoolBooks, schoolBooks.length+1);
        schoolBooks[schoolBooks.length-1] = book;
        return true;
    }

    @Override
    public SchoolBook[] findByName(String name) {
        SchoolBook[] findBooks = new SchoolBook[0];
        for (SchoolBook book: schoolBooks) {
            if (book.getName().equals(name)) {
                //копируем созданный в новый массив, добавляя 1 элемент в конец
                findBooks = Arrays.copyOf(findBooks, findBooks.length+1);
                findBooks[findBooks.length-1] = book;
            }
        }
        return findBooks;
    }

    @Override
    public boolean removeByName(String name) {
        //кол-во найденных книг
        int countOfNames = 0;
        int schoolBooksLength = schoolBooks.length;
        //проверяем каждую книга на соответствие
        //если совпадает - двигаем массив на 1 влево и проверяем с того же индекса
        for (int i = 0; i < schoolBooksLength; i++) {
            if (schoolBooks[i].getName().equals(name)) {
                countOfNames += 1;
                if (i == schoolBooksLength-1) break;
                System.arraycopy(schoolBooks, i + 1, schoolBooks, i, schoolBooksLength-1 - i);
                i--;
            }
        }
        if (countOfNames == 0) return false;

        schoolBooks = Arrays.copyOf(schoolBooks, schoolBooksLength-countOfNames);
        return true;
    }

    @Override
    public int count() {
        return schoolBooks.length;
    }
}
