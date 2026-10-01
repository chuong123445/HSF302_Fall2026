package com.example.fu.de200388.ch4.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 100)
    private String code;
    @Column(nullable = false,length = 100)
    private String name;
    @Column(nullable = false)
    private Integer credits;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false,length = 10)
    private String semester;
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students= new HashSet<>();

    public Course( String code, String name, Integer credits, Integer capacity, String semester) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
        this.semester = semester;
    }

    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof Course other)) return false;
        return this.code !=null && this.code.equals(other.code);
    }
    public int hashCode(){
        return Objects.hashCode(this.code);
    }
    public String toString(){
        return String.format("%s | %-40s | %d credits | cap %d | %s",
                code,name,credits,capacity,semester);
    }

}
