package com.example.messageuserapp.Model;



import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "usr")
public class UserModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login,password;
    private Boolean enabled;
    private Roles roles;

    public UserModel(String login, String password,Roles roles,Boolean enabled) {
        this.login = login;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) && Objects.equals(login, userModel.login) && Objects.equals(password, userModel.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }
}
