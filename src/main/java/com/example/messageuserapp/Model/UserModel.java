package com.example.messageuserapp.Model;



import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "usr")
public class UserModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username,password;
    private Boolean enabled;
    private Roles roles;

    public UserModel(String username, String password,Roles roles,Boolean enabled) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
    }

    public UserModel() {
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) && Objects.equals(username, userModel.username) && Objects.equals(password, userModel.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
