package com.scm.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {

    @Id
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;
    private String about;
    private String profilePic;
    private boolean enabled=true;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;
    
    private String phoneNumber;


    @Enumerated(value = jakarta.persistence.EnumType.STRING)
    //SELF, GOOGLE, FACEBOOK, GITHUB,LINKEDIN
    private Providers provider=Providers.SELF;
    
    private String providerUserId;


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

    @OneToMany(mappedBy = "user",cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private  List<Contact> contacts=new ArrayList<>();
}
