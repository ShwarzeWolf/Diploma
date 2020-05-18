package volunteersservice.models.entities;

import volunteersservice.utils.Utils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @Column(name = "Surname", nullable = false)
    @NotNull
    @NotEmpty(message = "Surame cannot be empty")
    private String surname;

    @Column(name = "Email", nullable = false, unique = true)
    @NotNull
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;

    @Column(name = "ContactPhone", nullable = false)
    @NotNull
    @NotEmpty(message = "Contact phone cannot be empty")
    private String contactPhone;

    @Column(name = "RegisterDate", nullable = false)
    @NotNull
    private LocalDateTime registerDate;

    @Column(name = "PasswdHash1", nullable = false)
    @NotNull
    private String hash1;

    @Column(name = "PasswdHash2", nullable = false)
    @NotNull
    private String hash2;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RoleID", nullable = false)
    @NotNull
    UserRole userRole;

    public User() {
    }

    public User(User other) {
        this.userID = other.userID;
        this.login = other.login;
        this.name = other.name;
        this.surname = other.surname;
        this.email = other.email;
        this.registerDate = other.registerDate;
        this.hash1 = other.hash1;
        this.hash2 = other.hash2;
        this.userRole = other.userRole;
        this.contactPhone = other.contactPhone;
    }

    public User(String email, String login, String userName, String surname, LocalDateTime registerDate, String password,
            UserRole userRole, String contactPhone) {
        this.email = email;
        this.login = login;
        this.name = userName;
        this.surname = surname;
        this.registerDate = registerDate;
        this.hash1 = Utils.calcSHA256(password);
        this.hash2 = Utils.calcMD5(password);
        this.userRole = userRole;
        this.contactPhone = contactPhone;
    }

    public int getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {return surname; }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public String getHash1() {
        return hash1;
    }

    public String getHash2() {
        return hash2;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setHash1(String password) {
        this.hash1 = Utils.calcSHA256(password);
    }

    public void setHash2(String password) {
        this.hash2 = Utils.calcMD5(password);
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContacts(){
        return surname.concat(" ").concat(name).concat(", ").concat(email);
    }
    @Override
    public String toString() {
        return String.format("(User %d): %s; %s; %s; %s; %s + %s; %s %s", userID, email, login, name, surname,  hash1, hash2,
                registerDate, userRole);
    }
}