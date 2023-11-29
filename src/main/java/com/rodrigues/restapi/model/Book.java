package com.rodrigues.restapi.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 180)
    private String author;
    @Column(nullable = false, length = 180)
    private String title;
    @Column(nullable = false)
    private Double price;
    @Column(name = "launch_date", nullable = false)
    @Temporal(TemporalType.DATE)
    //adicionar annotation temporal quando utilizar JPA. @Temporal(TemporalType.DATE)
    private Date launchDate;

    public Book(Long id, String author, String title, Double price, Date launchDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
        this.launchDate = launchDate;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date releaseDate) {
        this.launchDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return String.format(
                "Book (id= %s, author= %s, title= %s, price= %s, releaseDate= %s)", this.id, this.author, this.title, this.price, this.launchDate);
    }
}
