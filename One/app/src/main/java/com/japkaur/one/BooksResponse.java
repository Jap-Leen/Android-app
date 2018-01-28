package com.japkaur.one;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by jap on 22/1/18.
 */

@Root(name="GoodreadsResponse",strict = false)
public class BooksResponse {


    @Element(name="search")
    public Search search;
    public Search getSearch(){
        return search;
    }

    public BooksResponse(){

    }


}