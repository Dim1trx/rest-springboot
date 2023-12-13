package com.rodrigues.restapi.data.vo.v1.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id", "title", "author", "price", "release_date"})
public class BookVOV1 extends RepresentationModel<BookVOV1> implements Serializable {
    @Mapping("id")
    @JsonProperty("id")
    private Long key;
    private String title;
    private String author;
    private Double price;
    @JsonProperty("launch_date")
    private Date launchDate;

    public BookVOV1() {
    }

    public BookVOV1(Long key, String title, String author, Double price, Date launchDate) {
        this.key = key;
        this.title = title;
        this.author = author;
        this.price = price;
        this.launchDate = launchDate;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookVOV1 bookVOV1 = (BookVOV1) o;
        return Objects.equals(key, bookVOV1.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key);
    }
}
