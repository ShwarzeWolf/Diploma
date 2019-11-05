package volunteersservice.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import volunteersservice.utils.Utils;

@Entity
@Table(name = "VolunteersService.Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private int userID;

    @Column(name = "Login", nullable = false, unique = true)
    @NotNull
    @NotEmpty(message = "Login cannot be empty")
    private String login;

    @Column(name = "Name", nullable = false)
    @NotNull
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column(name = "Email", nullable = false, unique = true)
    @NotNull
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;

    @Column(name = "RegisterDate", nullable = false)
    @NotNull
    private Timestamp registerDate;

    @Column(name = "PasswdHash1", nullable = false)
    @NotNull
    private String hash1;

    @Column(name = "PasswdHash2", nullable = false)
    @NotNull
    private String hash2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TypeID", nullable = false)
    @NotNull
    UserType userType;

    public User() {
    }

    public User(User other) {
        if (this == other)
            return;
        this.userID = other.userID;
        this.login = other.login;
        this.name = other.name;
        this.email = other.email;
        this.registerDate = other.registerDate;
        this.hash1 = other.hash1;
        this.hash2 = other.hash2;
        this.userType = other.userType;
    }

    public User(String email, String login, String userName,
            Timestamp registerDate, String password, UserType userType) {
        this.email = email;
        this.login = login;
        this.name = userName;
        this.registerDate = registerDate;
        this.hash1 = Utils.calcSHA256(password);
        this.hash2 = Utils.calcMD5(password);
        this.userType = userType;
    }

    public int getID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHash1() {
        return hash1;
    }

    public String getHash2() {
        return hash2;
    }

    @Override
    public String toString() {
        return String.format("(User) %d: %s; %s; %s; %s + %s; %s %s", userID, email, login, name, hash1, hash2,
                registerDate, userType);
    }
}