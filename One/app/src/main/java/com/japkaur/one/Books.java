package com.japkaur.one;

/**
 * Created by jap on 20/1/18.
 */
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Books {
      @Element(required = false)
    String title;
    @Element(required = false)
    String id;
    @Element(required = false)
    String author;
    @Element(required = false)
    String quantity;
    @Element(required = false)
    String price;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity=quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price=price;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author=author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title=title;
    }


}