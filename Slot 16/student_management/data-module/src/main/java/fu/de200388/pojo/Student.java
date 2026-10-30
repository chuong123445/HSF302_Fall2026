package fu.de200388.pojo;


import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

// TODO 1.1: @Entity, @Table(name = "students")
public class Student {

    // TODO 1.2: @Id + @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO 1.3: @Nationalized + @Column(nullable = false, length = 100)
    private String name;

    // TODO 1.4: @Column(nullable = false, unique = true, length = 150)
    private String email;

    // TODO 1.5: @Column(nullable = false)
    private Integer age;

    // TODO 1.6: constructor rỗng + (name, email, age) + (id, name, email, age)
    // TODO 1.7: getter/setter đủ 4 field
    // TODO 1.8: toString() "id | name | email | age"
}
