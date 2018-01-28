package com.japkaur.one;

import org.simpleframework.xml.Element;

/**
 * Created by jap on 22/1/18.
 */

public class BooksResponse {
    @Element(required = false)
    Book book;

    @Override
    public String toString() {
        return "BookShowResponse{" +
                "book=" + book +
                '}';
    }

    public Book getBook() {
        return book;
    }
}