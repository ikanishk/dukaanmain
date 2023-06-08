package com.example.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="users")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer id;

//@NotEmpty
//@Column(nullable=false)
private String firstName;

private String lastName;

//@Column(nullable=false,unique=true)
@NotEmpty
@Email(message="{errors.invalid_email}")
private String email;

//@NotEmpty
private String password;

//The below segment needs to be understood
//Here we are mapping a many-many relationship between the user and role table as one user can have multiple roles and vice versa.
@ManyToMany(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
@JoinTable(
		name="user_role",
		joinColumns = {@JoinColumn(name="USER_ID",referencedColumnName = "ID")},
		inverseJoinColumns = {@JoinColumn(name="ROLE_ID",referencedColumnName = "ID")}
		)

private List<Role> roles;

// why did we pass user object in the below constructor ?

public User(User user) {

	this.id = id;
	this.firstName = user.getFirstname();
	this.lastName = user.getLastname();
	this.email = user.getEmail();
	this.password = user.getPassword();
	this.roles = user.getRoles();
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getFirstname() {
	return firstName;
}

public void setFirstname(String firstname) {
	this.firstName = firstname;
}

public String getLastname() {
	return lastName;
}

public void setLastname(String lastname) {
	this.lastName = lastname;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public List<Role> getRoles() {
	return roles;
}

public void setRoles(List<Role> roles) {
	this.roles = roles;
}


@Override
public String toString() {
	return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
			+ password + ", roles=" + roles + "]";
}

//default constructor
public User(){

}



}
