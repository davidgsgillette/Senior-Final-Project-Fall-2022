package edu.sru.group3.WebBasedEvaluations.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.company.Location;
import edu.sru.group3.WebBasedEvaluations.domain.MyUserDetails;
import edu.sru.group3.WebBasedEvaluations.domain.Role;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.excel.ExcelRead_group;
import edu.sru.group3.WebBasedEvaluations.repository.CompanyRepository;
import edu.sru.group3.WebBasedEvaluations.repository.DepartmentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.RoleRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;
import edu.sru.group3.WebBasedEvaluations.service.AdminMethodsService;
import edu.sru.group3.WebBasedEvaluations.service.UserService;

/**
 * Class for housing the methods for controlling how to add users via
 * manually/uploading
 * 
 * @author Dalton Stenzel
 *
 */
@Controller
public class AddUserController {
	// set up a UserRepositoty variable
	private UserRepository userRepository;
	private RoleRepository roleRepo;
	private CompanyRepository companyRepo;
	private LocationRepository locationRepo;
	private DepartmentRepository deptRepo;

	@Autowired
	private AdminMethodsService adminMethodsService;

	@Autowired
	private EvaluatorRepository evalRepo;


	@Autowired
	private EvaluationRepository evalFormRepo;

	private Logger log = LoggerFactory.getLogger(AdminMethodsService.class);

	public AddUserController(UserRepository userRepository,RoleRepository roleRepository,CompanyRepository companyRepo,LocationRepository locationRepo, DepartmentRepository deptRepo) {
		this.deptRepo = deptRepo;
		this.userRepository = userRepository;
		this.roleRepo = roleRepository;
		this.companyRepo = companyRepo;
		this.locationRepo = locationRepo;
	}

	//	@PostMapping
	//	(value = "/adduser/")
	//    public ResponseEntity<User> addUserPost(HttpServletRequest request,
	//                                        UriComponentsBuilder uriComponentsBuilder) {
	//
	//        var content = request.getParameter("content");
	//
	//        String name = request.getParameter("name");
	//    	String firstName = request.getParameter("firstName");
	//    	String lastName = request.getParameter("lastName");
	//    	String email = request.getParameter("email");
	//    	String password = request.getParameter("password");
	//    	String role = request.getParameter("role");
	//		String dateOfHire = request.getParameter("dateOfHire");
	//		String jobTitle = request.getParameter("jobTitle");
	//		String supervisor = request.getParameter("supervisor");
	//		String divisionBranch = request.getParameter("divisonBranch");
	//		Company co = new Company(null);
	//        
	//        User user = new User(name, firstName, lastName, email, password, role, 1, dateOfHire, jobTitle, supervisor, divisionBranch, co);
	//        
	//        //this.addUser(user);
	//
	//        UriComponents uriComponents =
	//                uriComponentsBuilder.path("/admin_users/{id}").buildAndExpand(user.getId());
	//        var location = uriComponents.toUri();
	//
	//        return ResponseEntity.created(location).build();
	//    }

	/**
	 * Method for manually adding users from the admin user page. It calls a few
	 * methods from the AdminMethodsService class for checking for any changes,
	 * capital letters, spaces, problems, etc,. This method is called when the "Add
	 * User" button is pressed.
	 * 
	 * @param user     is a User object used that holds the information submitted
	 *                 from the "Add User" button
	 * @param result   is a BindResult object used in conjunction with "@Validated"
	 *                 tag in order to bind the submission to an object.
	 * @param model    is a Model object used for adding attributes to a webpage,
	 *                 mostly used for adding messages and lists to the page.
	 * @param auth	   The authentication of the currently logged in user. 
	 * @param keyword  is a String used to hold a particular set of characters being
	 *                 sought after in a list of users.
	 * @param perPage  is an Integer value used to store the amount of users to be
	 *                 displayed per page.
	 * @param sort     is a String value user to contain the type of sorting to be
	 *                 used on a list of users such as: first name, last name,
	 *                 email, role.
	 * @param currPage is an Integer value used to store the current page number the
	 *                 user was on.
	 * @param sortOr   is an Integer value used to determine the order in which the
	 *                 sort will take place: ascending(1) or descending(0).
	 * @return admin_users html webpage.
	 */
	@RequestMapping(value = "/adduser/", method = RequestMethod.POST)
	public String addUser(@Validated User user, BindingResult result, Model model, Authentication auth,
			/* @RequestParam("keyword") */ String keyword, @RequestParam("perPage") Integer perPage,
			@RequestParam("sort") String sort, @RequestParam("currPage") Integer currPage,
			@RequestParam("sortOr") Integer sortOr) {
		String ansr = null;
		String mess = null;		
		boolean check = false;
		user.setAdminEval(false);
		user.setCompanySuperUser(false);
		user.setSuperUser(false);
		User adminUser = user;
		
		User currentUser;
		Company currentCompany;
		
		MyUserDetails userD = (MyUserDetails) auth.getPrincipal();

		Long idnum = userD.getID();

		currentUser = this.userRepository.findById(idnum).orElse(null);

		currentCompany = currentUser.getCompany();

		if (userRepository.findByEmail(user.getEmail()) == null) {

			check = adminMethodsService.checkAndUpdate(user);
//			boolean missedField = false;
			//check if admin has permission to add this user to the dept/location/role
			//departments of the user being added

			Department dept = deptRepo.findByNameAndCompany(user.getDepartmentName(), currentCompany);
			Location loc = locationRepo.findByLocationNameAndCompany(user.getDivisionBranch(), adminUser.getCompany());
			Company co = companyRepo.findByCompanyName(user.getCompanyName());
			user.setCompany(co);
			Role role = roleRepo.findByNameAndCompany(user.getRole().getName(),co);


			try {
				System.out.println(user.getRoleName());
				System.out.println(user.getRole().getName());
			}
			catch (Exception e) {
				System.out.println("doesnt have a role");
			}

			if(role != null) {
				if(adminUser.getRole().contains(role) || adminUser.isCompanySuperUser() || adminUser.isSuperUser()) {
					user.setRole(role);
				}
				else {
					mess = "User " + adminUser.getName() + " does not have permission to grant a user the " + role.getName() + " role.";
					check = false;
				}
			}
			else {			
				Role newRole = new Role(user.getRoleName(), currentCompany);
				co.addRole(newRole);
				user.setRole(newRole);
			}
			if(dept != null) {
				if((adminUser.getRole().writableDepartments().contains(dept) || adminUser.isCompanySuperUser() || adminUser.isSuperUser()) && !user.getDepartments().contains(dept)) {
					user.addDepartment(dept);
				}
				else if(!user.getDepartments().contains(dept))
				{
					mess = "User " + adminUser.getName() + " already has the dept: " + dept.getName();
				}
				else{
					mess = "User " + adminUser.getName() + " does not have permission to add a user to department " + dept.getName();
					check = false;
				}
			}
			if(loc != null) {
				if(adminUser.getRole().writableLocations().contains(loc) || adminUser.isCompanySuperUser() || adminUser.isSuperUser()) {
					user.addLocation(loc);
				}
				else {
					mess = "User " + adminUser.getName() + " does not have permission to add a user to location " + loc.getLocationName();
					check = false;
				}
			}

			if (check == true) {





				user.setFirstName(adminMethodsService.capitalize(user.getFirstName()));
				user.setLastName(adminMethodsService.capitalize(user.getLastName()));
				if (user.getSuffixName() == " " || user.getSuffixName() == null) {

					user.setName(user.getFirstName() + " " + user.getLastName());

				} else {

					user.setName(user.getFirstName() + " " + user.getLastName() + " " + user.getSuffixName());

				}
				
				if(user.getRole().getName().contains("ADMIN_EVAL")) {
					user.setAdminEval(true);
				}

				user.setEncryptedPassword(user.getPassword());
				user.setReset(true);
				boolean worked = true;
				try {
					userRepository.save(user);
				}
				catch(Exception e) {
					
					
//					missedField = true;
//					model.addAttribute("missedField", missedField);
//					model.addAttribute("enteredEmail", user.getEmail());
//					
					ansr = "addFail";
					mess = "Error occured!";	
					worked=false;
					log.error(e.getStackTrace().toString());
				}

				if(worked) {
					log.info("ADMIN User - ID:" + adminUser.getId() + " First Name: " + adminUser.getFirstName()
					+ " Last Name: " + " added a " + user.getRole().getName() + " user");

					ansr = "addPass";
					mess = "User successfully added!";				
					// adminMethodsService.adminUserPageItems(ansr, keyword, mess, perPage, model,
					// sort);
				}
			} else {
				ansr = "addFail";
				if(mess == null) {
					
					mess = "User not added! A field was blank, contained spaces or the password wasn't at least 5 characters!";
				}
				// adminUserPageItems(ansr, keyword, mess, perPage, model, sort);
			}
			adminMethodsService.adminUserPageItems(ansr, keyword, mess, perPage, model, sort, currPage, sortOr, auth);
			model = AdminMethodsService.pageNavbarPermissions(adminUser, model, this.evalRepo, evalFormRepo);
			model = AdminMethodsService.addingOrEditingUser(adminUser, this.locationRepo, this.deptRepo, this.roleRepo, this.companyRepo, model);
			return "admin_users";
		}

		model = AdminMethodsService.pageNavbarPermissions(adminUser, model, this.evalRepo, evalFormRepo);
		model = AdminMethodsService.addingOrEditingUser(adminUser, this.locationRepo, this.deptRepo, this.roleRepo, this.companyRepo, model);

		if (result.hasErrors()) {

			model.addAttribute("users", userRepository.findAll());
			return "admin_users";
		}

		else {
			ansr = "addFail";
			mess = "User email already taken!";

			adminMethodsService.adminUserPageItems(ansr, keyword, mess, perPage, model, sort, currPage, sortOr, auth);
			return "admin_users";

		}

	}

	/**
	 * Method called when the "Upload Users" button is pressed. It will attempt
	 * check the file uploaded, if there is one in the first place, and will log
	 * information about users unable to be added. It checks if there was a file
	 * selected and also adds messages about when users were or were not
	 * successfully added.
	 * 
	 * @param reapExcelDataFile is a MultipartFile object
	 * @param user              is a User object that is required for the page,
	 *                          other an IllegalStateException error will occur.
	 * 
	 * @param model             is a Model object used for adding attributes to a
	 *                          webpage, mostly used for adding messages and lists
	 *                          to the page.
	 * @param keyword           is a String used to hold a particular set of
	 *                          characters being sought after in a list of users.
	 * @param perPage           is an Interger value used to store the amount of
	 *                          users to be displayed per page.
	 * @param sort              is a String value user to contain the type of
	 *                          sorting to be used on a list of users such as: first
	 *                          name, last name, email, role.
	 * @param currPage          is an Integer value used to store the current page
	 *                          number the user was on.
	 * @param sortOr            is an Integer value used to determine the order in
	 *                          which the sort will take place: ascending(1) or
	 *                          descending(0).
	 * @return admin_users html webpage.
	 */
	@RequestMapping(value = "/uploaduser2", method = RequestMethod.POST)
	public Object uploaduser2(@RequestParam("file") MultipartFile reapExcelDataFile,
			@RequestParam("perPage") Integer perPage, @Validated User users, /* BindingResult result, */ Model model,
			String keyword, @RequestParam("sort") String sort, @RequestParam("currPage") Integer currPage,
			@RequestParam("sortOr") Integer sortOr, Authentication auth) {

		String ansr;
		String mess;
		
		
		User currentUser;
		Company currentCompany;
		
		MyUserDetails userD = (MyUserDetails) auth.getPrincipal();

		Long idnum = userD.getID();

		currentUser = this.userRepository.findById(idnum).orElse(null);

		currentCompany = currentUser.getCompany();
		
		boolean check = false;
		XSSFSheet sheet = null;
		try {
			sheet = ExcelRead_group.loadFile(reapExcelDataFile).getSheetAt(0);

		} catch (Exception e) {
			if (e instanceof Exception) {

				mess = "No file selected!";
				ansr = "addFail";
				adminMethodsService.adminUserPageItems(ansr, keyword, mess, perPage, model, sort, currPage, sortOr,auth);
				model = AdminMethodsService.pageNavbarPermissions(currentUser, model, this.evalRepo, evalFormRepo);
				model = AdminMethodsService.addingOrEditingUser(currentUser, this.locationRepo, this.deptRepo, this.roleRepo, this.companyRepo, model);
				return "admin_users";

			}
		}

		if (ExcelRead_group.checkStringType(sheet.getRow(0).getCell(1)).equals(null)
				|| !ExcelRead_group.checkStringType(sheet.getRow(0).getCell(1)).equals("User Upload")) {

		}

		else if (ExcelRead_group.checkStringType(sheet.getRow(0).getCell(1)).equals("User Upload")) {

			for (int i = 2; sheet.getRow(i) != null; i++) {
				try {
					User user2 = new User();
					User tempUser = userRepository
							.findByEmail(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(3)));
					if (tempUser == null) {
						if (ExcelRead_group.checkStringType(sheet.getRow(i).getCell(2)) == " "
								|| ExcelRead_group.checkStringType(sheet.getRow(i).getCell(2)) == null) {

							String str1 = adminMethodsService
									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(0)));
							String str2 = adminMethodsService
									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(1)));

							user2.setName(str1 + " " + str2);
							user2.setFirstName(str1);
							user2.setLastName(str2);

						} else {
							String str1 = adminMethodsService
									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(0)));
							String str2 = adminMethodsService
									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(1)));
							String str3 = adminMethodsService
									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(2)));

							user2.setName(str1 + " " + str2 + " " + str3);
							user2.setFirstName(str1);
							user2.setLastName(str2);
							user2.setSuffixName(str3);

						}
						user2.setEmail(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(3)));
						user2.setPassword(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(4)));

						//if the role exists add it to the user, otherwise create a role that has that name, but no permissions. 
						String roleName = (ExcelRead_group.checkStringType(sheet.getRow(i).getCell(5)));
						user2.setDateOfHire(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(7)));
						user2.setJobTitle(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(8)));
						String deptName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(9));		
						user2.setDepartmentName(deptName);
						boolean setAsDeptManager = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(10));
						String companyName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(11));
						user2.setCompanyName(companyName);
						String locationName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(12));
						Location loc = this.locationRepo.findByLocationNameAndCompany(locationName, currentCompany);


						user2.setDivisionBranch(locationName);


						Company co = companyRepo.findByCompanyName(companyName);

						//check that the company exists and that the logged in user has permission to add a user to it. 
						if(co != null ){

							if(co.getId() == currentCompany.getId()){
								user2.setCompany(co);
							}
							else {
								throw new Exception("User:" + currentUser.getName() + " does not have access to company " + companyName);
							}
						}
						else {
							throw new Exception("Company" + companyName + " does not exist, please add it before attempting to add users to it.");
						}

						//check that the location exists and that the logged in user has permission to add a user to it. 
						if(loc != null ){
							if(currentUser.getRole().writableLocations().contains(loc) || currentUser.isCompanySuperUser() || currentUser.isSuperUser()){
								user2.addLocation(loc);
							}
							else {
								throw new Exception("User:" + currentUser.getName() + " does not have access to location " + locationName);
							}
						}
						else {
							if(currentUser.getRole().writableLocations().contains(loc) || currentUser.isCompanySuperUser() || currentUser.isSuperUser()){
								loc = new Location(locationName,null,currentCompany,null);
								user2.addLocation(loc);
							}
							else {
								throw new Exception("User:" + currentUser.getName() + " does not have access to location " + locationName);
							}
						}

						Role role = roleRepo.findByNameAndCompany(roleName,currentCompany);
						if(role != null) {
							if(currentUser.getRole().contains(role) || currentUser.isCompanySuperUser() || currentUser.isSuperUser()) {
								user2.setRole(role);
							}
							else {
								throw new Exception("current user " + currentUser.getName() + " does not have permission to assign role "  +role.getName()+ " to a user.");
							}
						}
						else {							
							Role newRole = new Role(roleName,currentCompany);
							//							newRole.addCompany(currentCompany);
							user2.setRole(newRole);

						}

						//sets the department and supervisor based on the dept we are adding the user to. 
						Department dept = this.deptRepo.findByNameAndCompany(deptName, currentCompany);
						if(dept != null) {
							if(currentUser.getRole().writableDepartments().contains(dept) || currentUser.isCompanySuperUser() || currentUser.isSuperUser()) {

								if(setAsDeptManager || dept.getDeptHead() == null) {
									dept.setDeptHead(user2);
									user2.addSubDept(dept);
									for(User user : dept.getUsers()) {
										user.setSupervisor(user2.getName());
									}
								}
								else {
									user2.setSupervisor(dept.getDeptHead().getName());
								}
								user2.addDepartment(dept);
								dept.addUser(user2);
							}
							else {
								throw new Exception("current user " + currentUser.getName() + " does not have permission to add user "  +user2.getName()+ " to a dept " + dept.getName());
							}
						}
						else {
							if(currentUser.getRole().writableDepartments().contains(dept) || currentUser.isCompanySuperUser() || currentUser.isSuperUser()) {
								//adds the user as the dept head since it is a fresh dept.
								dept = new Department(user2,loc,user2.getDepartmentName(),null,user2,currentCompany);
								user2.addSubDept(dept);
								user2.addDepartment(dept);
								user2.setSupervisor(null);

							}
						}			

						user2.setReset(true);
						if(user2.getRole().getName().contains("ADM_EVAL")) {
							user2.setAdminEval(true);
						}



						userRepository.save(user2);
						check = true;

					}
					else {
						log.error("User " + tempUser.getEmail() + " already exists.");
					}

				}

				catch (Exception e) {

					log.error("Could not add user in row: " + (sheet.getRow(i).getRowNum() + 1) + " from "
							+ reapExcelDataFile.getOriginalFilename()
							+ ". Either null, email already taken, or incorrect information!\n" + e.getMessage());
					model.addAttribute("log", "error");
				}
			}
		}
		model = AdminMethodsService.pageNavbarPermissions(currentUser, model, this.evalRepo, evalFormRepo);
		model = AdminMethodsService.addingOrEditingUser(currentUser, this.locationRepo, this.deptRepo, this.roleRepo, this.companyRepo, model);


		if (check) {
			log.info("ADMIN User - ID:" + currentUser.getId() + " First Name: " + currentUser.getFirstName()
			+ " Last Name: " + " uploaded a file: " + reapExcelDataFile.getOriginalFilename());

			mess = "File uploaded! User(s) successfully added!";
			ansr = "addPass";

			adminMethodsService.adminUserPageItems(ansr, keyword, mess, perPage, model, sort, currPage, sortOr, auth);
			model = AdminMethodsService.pageNavbarPermissions(currentUser, model, this.evalRepo, evalFormRepo);
			model = AdminMethodsService.addingOrEditingUser(currentUser, this.locationRepo, this.deptRepo, this.roleRepo, this.companyRepo, model);

			return "admin_users";

		} else {

			mess = "File failed to be uploaded!";
			ansr = "addFail";

			adminMethodsService.adminUserPageItems(ansr, keyword, mess, perPage, model, sort, currPage, sortOr, auth);
			model = AdminMethodsService.pageNavbarPermissions(currentUser, model, this.evalRepo, evalFormRepo);
			model = AdminMethodsService.addingOrEditingUser(currentUser, this.locationRepo, this.deptRepo, this.roleRepo, this.companyRepo, model);

			return "admin_users";

		}

	}





	//	@RequestMapping(value = "/uploaduser2", method = RequestMethod.POST)
	//	public String uploaduser2(@RequestParam("file") MultipartFile reapExcelDataFile,
	//			@RequestParam("perPage") Integer perPage, @Validated User users, /* BindingResult result, */ Model model,
	//			String keyword, @RequestParam("sort") String sort, @RequestParam("currPage") Integer currPage,
	//			@RequestParam("sortOr") Integer sortOr, Authentication auth) {
	//
	//		String ansr;
	//		String mess;
	//		MyUserDetails userD = (MyUserDetails) auth.getPrincipal();
	//		User currentUser = userRepository.findByid(userD.getID());
	//		Company currentCompany = currentUser.getCompany();
	//		boolean check = false;
	//		XSSFSheet sheet = null;
	//		try {
	//			sheet = ExcelRead_group.loadFile(reapExcelDataFile).getSheetAt(0);
	//
	//		} catch (Exception e) {
	//			if (e instanceof Exception) {
	//
	//				mess = "No file selected!";
	//				ansr = "addFail";
	//				adminMethodsService.adminUserPageItems(ansr, keyword, mess, perPage, model, sort, currPage, sortOr,auth);
	//				return "admin_users";
	//
	//			}
	//		}
	//
	//		if (ExcelRead_group.checkStringType(sheet.getRow(0).getCell(1)).equals(null)
	//				|| !ExcelRead_group.checkStringType(sheet.getRow(0).getCell(1)).equals("User Upload")) {
	//
	//		}
	//
	//		else if (ExcelRead_group.checkStringType(sheet.getRow(0).getCell(1)).equals("User Upload")) {
	//
	//			for (int i = 2; sheet.getRow(i) != null; i++) {
	//				try {
	//					User user2 = new User();
	//					User tempUser = userRepository
	//							.findByEmail(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(3)));
	//					if (tempUser == null) {
	//						if (ExcelRead_group.checkStringType(sheet.getRow(i).getCell(2)) == " "
	//								|| ExcelRead_group.checkStringType(sheet.getRow(i).getCell(2)) == null) {
	//
	//							String str1 = adminMethodsService
	//									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(0)));
	//							String str2 = adminMethodsService
	//									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(1)));
	//
	//							user2.setName(str1 + " " + str2);
	//							user2.setFirstName(str1);
	//							user2.setLastName(str2);
	//
	//						} else {
	//							String str1 = adminMethodsService
	//									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(0)));
	//							String str2 = adminMethodsService
	//									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(1)));
	//							String str3 = adminMethodsService
	//									.capitalize(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(2)));
	//
	//							user2.setName(str1 + " " + str2 + " " + str3);
	//							user2.setFirstName(str1);
	//							user2.setLastName(str2);
	//							user2.setSuffixName(str3);
	//
	//						}
	//						user2.setEmail(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(3)));
	//						user2.setPassword(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(4)));
	//
	//						//if the role exists add it to the user, otherwise create a role that has that name, but no permissions. 
	//						String roleName = (ExcelRead_group.checkStringType(sheet.getRow(i).getCell(5)));
	//						user2.setDateOfHire(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(7)));
	//						user2.setJobTitle(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(8)));
	//						String deptName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(9));		
	//						user2.setDepartmentName(deptName);
	//						boolean setAsDeptManager = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(10));
	//						String companyName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(11));
	//						user2.setCompanyName(companyName);
	//						String locationName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(12));
	//						user2.setDivisionBranch(locationName);
	//
	//
	//						Company co = companyRepo.findByCompanyName(companyName);
	//						Location loc = locationRepo.findByLocationName(locationName);
	//
	//						//check that the company exists and that the logged in user has permission to add a user to it. 
	//						if(co != null ){
	//							
	//							if(co.getId() == currentCompany.getId()){
	//								user2.setCompany(co);
	//							}
	//							else {
	//								throw new Exception("User:" + currentUser.getName() + " does not have access to company " + companyName);
	//							}
	//						}
	//						else {
	//							throw new Exception("Company" + companyName + " does not exist, please add it before attempting to add users to it.");
	//						}
	//
	//						//check that the location exists and that the logged in user has permission to add a user to it. 
	//						if(loc != null ){
	//							if(currentUser.getRole().writableLocations().contains(loc)){
	//								user2.addLocation(loc);
	//							}
	//							else {
	//								throw new Exception("User:" + currentUser.getName() + " does not have access to location " + locationName);
	//							}
	//						}
	//						else {
	//							user2.addLocation(null);
	//						}
	//
	//						Role role = roleRepo.findByNameAndCompany(roleName,currentCompany);
	//						if(role != null) {
	//							if(currentUser.getRole().contains(role)) {
	//								user2.setRole(role);
	//							}
	//							else {
	//								throw new Exception("current user " + currentUser.getName() + " does not have permission to assign role "  +role.getName()+ " to a user.");
	//							}
	//						}
	//						else {							
	//							Role newRole = new Role(roleName,currentCompany);
	////							newRole.addCompany(currentCompany);
	//							user2.setRole(newRole);
	//							
	//						}
	//
	//						//sets the department and supervisor based on the dept we are adding the user to. 
	//						Department dept = this.deptRepo.findByName(deptName);
	//						if(dept != null) {
	//							if(currentUser.getRole().writableDepartments().contains(dept)) {
	//								user2.addDepartment(dept);
	//								if(setAsDeptManager) {
	//									dept.setDeptHead(user2);
	//								}
	//								user2.setSupervisor(dept.getDeptHead().getName());
	//							}
	//							else {
	//								throw new Exception("current user " + currentUser.getName() + " does not have permission to add user "  +user2.getName()+ " to a dept " + dept.getName());
	//							}
	//						}
	//						else {
	//							user2.addDepartment(null);
	//							user2.setSupervisor("N/A");
	//						}					
	//
	//
	//
	//
	//						user2.setReset(true);
	//
	//
	//
	//
	//						userRepository.save(user2);
	//						check = true;
	//
	//					}
	//					else {
	//						log.error("User " + tempUser.getEmail() + " already exists.");
	//					}
	//
	//				}
	//
	//				catch (Exception e) {
	//
	//					log.error("Could not add user in row: " + (sheet.getRow(i).getRowNum() + 1) + " from "
	//							+ reapExcelDataFile.getOriginalFilename()
	//							+ ". Either null, email already taken, or incorrect information!\n" + e.getMessage());
	//					model.addAttribute("log", "error");
	//				}
	//			}
	//		}
	//
	//		if (check) {
	//			log.info("ADMIN User - ID:" + currentUser.getId() + " First Name: " + currentUser.getFirstName()
	//			+ " Last Name: " + " uploaded a file: " + reapExcelDataFile.getOriginalFilename());
	//
	//			mess = "File uploaded! User(s) successfully added!";
	//			ansr = "addPass";
	//			adminMethodsService.adminUserPageItems(ansr, keyword, mess, perPage, model, sort, currPage, sortOr, auth);
	//
	//			return "admin_users";
	//
	//		} else {
	//
	//			mess = "File failed to be uploaded!";
	//			ansr = "addFail";
	//			adminMethodsService.adminUserPageItems(ansr, keyword, mess, perPage, model, sort, currPage, sortOr, auth);
	//			return "admin_users";
	//
	//		}
	//
	//	}
	/*
	 * @GetMapping("/uploadingUser") public String uploadgroup(Model model) {
	 * 
	 * return "upload_user"; }
	 */

}
