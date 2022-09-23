package edu.sru.group3.WebBasedEvaluations.company;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import edu.sru.group3.WebBasedEvaluations.domain.User;

/*
 * Class for methods of the company object. hold info such as number of employees and head of tree of locations. 
 * @author David Gillette
 */
@Entity
@Table(name = "company")
public class Company {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	private String companyName;
	
	@NonNull
	private int numEmployees;
	
	//TODO create CEO class.
	//@NonNull
	//private CEO ceo;
	
	//TODO: create location where hq is the head of the tree of locations. 
	//private Location hq;
	
	@NonNull
	private int numLocations;
	
	//maps company id to user. 
	@NonNull
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<User> users;
	//private List<User> users = new ArrayList<>();
	
	public Company() {
		
	}
	
	
	public Company(String companyName) {
		this.companyName = companyName;
		//this.ceo = ceo;
		this.numEmployees = 1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getNumEmployees() {
		return numEmployees;
	}

	public void setNumEmployees(int numEmployees) {
		this.numEmployees = numEmployees;
	}

	/*
	public User getCeo() {
		return ceo;
	}

	public void setCeo(User ceo) {
		this.ceo = ceo;
	}
	*/

	public int getNumLocations() {
		return numLocations;
	}

	public void setNumLocations(int numLocations) {
		this.numLocations = numLocations;
	}
/*
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	*/
	
	
}
	
