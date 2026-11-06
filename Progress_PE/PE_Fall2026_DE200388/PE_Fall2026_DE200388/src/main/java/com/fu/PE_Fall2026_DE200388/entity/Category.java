package com.fu.PE_Fall2026_DE200388.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
       @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name="category_id")
    private Integer id;
       @Column(name="category_name",columnDefinition = "nvarchar(100)",unique = true,nullable = false)
    private String name;

    public Category() {
    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
