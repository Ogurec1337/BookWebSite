package ru.Harevich.BookSite.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(name="book_cart",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name="cart_id"))
    private List<Book> books;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public int getTotal() {
        int total = 0;
        for(Book book:books){
            total+=book.getPrice();
        }
        return total;
    }
    public boolean contains(Book book){
        return books.contains(book);
    }
}
