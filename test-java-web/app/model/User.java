package model; 

import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.Id; 
import javax.persistence.Table; 
import java.io.Serializable;

@Entity(name = "User")
@Table(name = "users", schema = "kunderaexamples@cassandrapu") 

public class User implements Serializable { 

    public User(){}
    
    private static final long serialVersionUID = 6653425147548009598L;
        
    @Id
    @Column(name="user_Id")
    private String userId; 
    
    @Column(name="first_Name") 
    private String firstName; 
    
    @Column(name="last_Name") 
    private String lastName; 
    
    @Column(name="city") 
    private String city; 
    
    //Getters/ setters/ constructors go here 
    
    public void setUserId(String userId) {
		this.userId = userId;
	}
	
    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
    public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
    public void setCity(String city) {
		this.city = city;
	}
	
	
	
	public String getUserId() {
		return userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getCity() {
		return city;
	}
		
}