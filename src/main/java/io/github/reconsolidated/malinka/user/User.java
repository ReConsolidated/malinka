package io.github.reconsolidated.malinka.user;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name="Konto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="IdKonta")
    private Long id;
    @Column(name="NazwaUzytkownika")
    private String username;
    @Column(name="Imie")
    private String name;
    @Column(name="Nazwisko")
    private String surname;
    @Column(name="Email")
    private String email;
    @Column(name="Telefon")
    private String phoneNumber;
    @Column(name="Haslo")
    private String password;
    @Column(name="LoyaltyPoints")
    private int loyaltyPoints;
    @Column(name="CreatedAt")
    private long createdTimestamp;
    @Column(name="UpdatedAt")
    private long updatedTimestamp;

    public User(long id, String username, String name, String surname, String password, int loyaltyPoints) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.loyaltyPoints = loyaltyPoints;
    }
}
