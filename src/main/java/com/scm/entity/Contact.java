package com.scm.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    private String id;

    private String name;
    private String email;      // fixed typo
    private String phoneNumber;
    private String address;
    private String picture;
    private boolean favourite = false; // fixed type

    /*
 Summary of @OneToMany(mappedBy):
 --------------------------------
 - @Entity classes are automatically mapped to DB tables by Hibernate.
 - @Table is optional; used only to override default table names.
 - In our case:
     User  -> "users" table
     Contact -> "contact" table
 - The @ManyToOne in Contact creates a foreign key column (user_userId).
 - Using mappedBy = "user" tells Hibernate that Contact owns the FK.
   => Only 2 tables: users + contact (with FK to users).
 - If mappedBy is NOT used, Hibernate creates an extra join table (users_contacts).
 - Tables are created/updated at runtime because of:
       spring.jpa.hibernate.ddl-auto=update
*/

    @ManyToOne
    private User user;

    
//     Example in Real Life

// User (Ram)

// has 5 Contacts: e.g., Ravi, Sita, Mohan, …

// Each Contact can have multiple SocialLinks:

// Ravi → LinkedIn + Facebook

// Sita → GitHub + Twitter

// Mohan → only LinkedIn
    @OneToMany(mappedBy = "contact")
    private List<SocialLinks> socialLinks=new ArrayList<>();

    

}
