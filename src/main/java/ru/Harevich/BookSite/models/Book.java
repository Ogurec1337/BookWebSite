package ru.Harevich.BookSite.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min = 2,max = 20,message = "wrong username size")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Min(value = 0)
    @Column(name = "price")
    private int price;

    @Column(name="url")
    @NotEmpty
    private String url;

    @Column(name="author")
    @NotEmpty
    private String author;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(name = "year")
    private int year;



    public @NotEmpty String getAuthor() {
        return author;
    }

    public void setAuthor(@NotEmpty String author) {
        this.author = author;
    }

    public @NotEmpty String getUrl() {
        return url;
    }

    public void setUrl(@NotEmpty String url) {
        this.url = url;
    }

    @ManyToMany(mappedBy = "books")
    private List<Cart> carts;

    public int getId() {
        return id;
    }

    public Book() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
