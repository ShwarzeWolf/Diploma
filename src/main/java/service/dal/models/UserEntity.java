package service.dal.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import service.utils.Utils;

@Entity
@Table (name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "PasswdHash1")
    private String hash1;

    @Column(name = "PasswdHash2")
    private String hash2;

    public UserEntity() {
    }

    public UserEntity(UserEntity other) {
        if (this == other)
            return;
        this.name = other.name;
        this.email = other.email;
        this.hash1 = other.hash1;
        this.hash2 = other.hash2;
    }

    public UserEntity(String userName, String email, String password) {
        this.name = userName;
        this.email = email;
        this.hash1 = Utils.calcSHA256(password);
        this.hash2 = Utils.calcMD5(password);
    }

    public int getID() {
        return id;
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
        return String.format("(UserEntity) %d: %s; %s; %s + %s", id, name, email, hash1, hash2);
    }
}