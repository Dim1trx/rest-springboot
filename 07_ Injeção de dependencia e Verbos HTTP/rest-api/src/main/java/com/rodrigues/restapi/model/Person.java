package com.rodrigues.restapi.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Table(name = "people")
@Entity
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", length = 80, nullable = false)
    private String name;
    @Column(name = "last_name", length = 80)
    private String lastName;
    @Column(length = 100)
    private String address;
    @Column(nullable = false)
    private String gender;

    public Person(Long id, String name, String lastName, String address, String gender) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
    }

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format(
                "Person (id= %s, name= %s, lastName= %s, address= %s, gender= %s)", this.id, this.name, this.lastName, this.address, this.gender);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
