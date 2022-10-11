package edu.sru.group3.WebBasedEvaluations.domain;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCrypt;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.company.Location;

/**Class for methods of a user object, almost exclusively made out of getters and setters.
 * @author Tanuj Rane, Dalton Stenzel, Logan Racer, David Gillette
 *
 */
@Entity
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	private String name;

	@NonNull
	private String firstName;

	@NonNull
	private String lastName;

	private String suffixName;

	@NonNull
	private String email;
	@NonNull
	private String password;
	@NonNull
	private String roleName;

	@NonNull
	private boolean resetP;

	@NonNull
	private String companyName;


	//@OneToMany(mappedBy = "user")
	//private List<Evaluator> evaluator = new ArrayList<>();


	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;


	@ManyToMany
	@JoinTable(
			name = "user_location", 
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "location_id"))
	private List<Location> locations;
	
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	
	
	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Department> departments;


	// Preload
	//private long employeeId;
	private String dateOfHire;
	private String jobTitle;
	private String supervisor;
	private String divisionBranch;

	public User() {
		
	}

	//adds user to a no location
	public User(String name, String firstName, String lastName, String email, String password, /*String role,*/
			int employeeId, String dateOfHire, String jobTitle, String supervisor,
			String divisionBranch, Company co) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = name;
		this.email = email;
		this.password = password;
		
		this.resetP = true;
		//this.companyID = companyID;
		this.companyName = co.getCompanyName();
		this.company = co;
		
		//this.employeeId = employeeId;
		this.dateOfHire = dateOfHire;
		this.jobTitle = jobTitle;
		this.supervisor = supervisor;
		this.divisionBranch = divisionBranch;
		this.locations = new ArrayList<Location>();
		this.departments = new ArrayList<Department>(); 
		this.role = new Role("NO ROLE ASSIGNED");
		this.roleName = role.getName();


	}

	//adds user to a single location
	public User(String name, String firstName, String lastName, String email, String password, /*String roleStr,*/
			int employeeId, String dateOfHire, String jobTitle, String supervisor, 
			String divisionBranch, Company co, Location location, Department dept, Role role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = name;
		this.email = email;
		this.password = password;
		this.resetP = true;
		//this.companyID = companyID;
		this.companyName = co.getCompanyName();
		this.company = co;
		// Preload
		//this.employeeId = employeeId;
		this.dateOfHire = dateOfHire;
		this.jobTitle = jobTitle;
		this.supervisor = supervisor;
		this.divisionBranch = divisionBranch;
		this.locations = new ArrayList<Location>();
		this.locations.add(location);
		this.departments = new ArrayList<Department>(); 
		this.departments.add(dept);
		this.role = role;
		if(this.role == null) {
			this.role = new Role("NO ROLE ASSIGNED");
		}
		this.roleName = role.getName();
		

	}

	//adds user to multiple locations. 
	public User(String name, String firstName, String lastName, String email, String password, /*String role,*/
			int employeeId, String dateOfHire, String jobTitle, String supervisor,
			String divisionBranch, Company co, List<Location> locations, List<Department> depts, Role role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = name;
		this.email = email;
		this.password = password;
		
		this.resetP = true;
		//this.companyID = companyID;
		this.companyName = co.getCompanyName();
		this.company = co;
		// Preload
		//this.employeeId = employeeId;
		this.dateOfHire = dateOfHire;
		this.jobTitle = jobTitle;
		this.supervisor = supervisor;
		this.divisionBranch = divisionBranch;
		this.locations = locations;
		this.departments = depts;
		this.role = role;
		if(this.role == null) {
			this.role = new Role("NO ROLE ASSIGNED");
		}
		this.roleName = role.getName();

	}
	
	public void addDepartment(Department dept) {
		this.departments.add(dept);
	}
	public List<Department> getDepartments() {
		return departments;
	}

//	public String getRolesStr() {
//		return rolesStr;
//	}
//
//	public void setRolesStr(String rolesStr) {
//		this.rolesStr = rolesStr;
//	}

	public void setRole(Role role) {
		this.role = role;
		this.roleName = role.getName();
	}

	public Role getRole() {
		if(this.role ==null) {
			return new Role("NO ROLE ASSIGNED");
		}
		return role;
	}
	
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public boolean removeDepartment(Department dept) {
		if(this.departments.contains(dept)) {
			this.departments.remove(dept);
			return true;
		}
		return false;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {

		// String encodPass = BCrypt.hashpw(password, BCrypt.gensalt());
		this.password = password/* .replaceAll("\\s", "") */;
	}

	public void setEncryptedPassword(String password) {
		String encodPass = BCrypt.hashpw(password/* .replaceAll("\\s", "") */, BCrypt.gensalt());
		this.setPassword(encodPass);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email/*.replaceAll("\\s", "")*/;
	}



	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public void addLocation(Location loc) {
		this.locations.add(loc);
	}
	
	public void addLocations(List<Location> locations) {
		for(Location loc : locations) {
			this.locations.add(loc);
		}
	}
	
	public boolean removeLocation(Location loc) {
		if(this.locations.contains(loc)) {
			this.locations.remove(loc);
			return true;
		}
		return false;
	}
		
	public String getRoleName() {
		return roleName;
	}

	/*
	public List<Evaluator> getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(List<Evaluator> evaluator) {
		this.evaluator = evaluator;
	}
	 */
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName/* .replaceAll("\\s", "") */;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName/* .replaceAll("\\s", "") */;
	}

	public String getSuffixName() {
		return suffixName;
	}

	public void setSuffixName(String suffixName) {
		if (suffixName == null) {
			this.suffixName = suffixName;
		} else if (suffixName.replaceAll("\\s", "") == "") {
			this.suffixName = null;
		}

		else {

			this.suffixName = suffixName.replaceAll("\\s", "");
		}
	}

	public boolean getReset() {
		return resetP;
	}

	public void setReset(boolean reset) {
		this.resetP = reset;
	}

	public boolean isResetP() {
		return resetP;
	}

	public void setResetP(boolean resetP) {
		this.resetP = resetP;

	}

	// Preload =======================================
	/*
	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	 */
	public String getDateOfHire() {
		return dateOfHire;
	}

	public void setDateOfHire(String dateOfHire) {
		if(dateOfHire.matches("[0-9]+")) {
			dateOfHire = LocalDate.of( 1899 , Month.DECEMBER , 30 ).plusDays((long) Double.parseDouble(dateOfHire)).toString();
		}
		this.dateOfHire = dateOfHire;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDivisionBranch() {
		return divisionBranch;
	}

	public void setDivisionBranch(String divisionBranch) {
		this.divisionBranch = divisionBranch;
	}


	public Long getCompanyID() {
		return id;
	}

	public void setCompanyID(Long companyID) {
		this.id = companyID;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	
	
	


}