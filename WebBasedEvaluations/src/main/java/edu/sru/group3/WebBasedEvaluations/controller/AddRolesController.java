package edu.sru.group3.WebBasedEvaluations.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.company.Location;
import edu.sru.group3.WebBasedEvaluations.company.LocationGroup;
import edu.sru.group3.WebBasedEvaluations.domain.MyUserDetails;
import edu.sru.group3.WebBasedEvaluations.domain.Privilege;
import edu.sru.group3.WebBasedEvaluations.domain.Role;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.excel.ExcelRead_group;
import edu.sru.group3.WebBasedEvaluations.repository.DepartmentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationGroupRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.PrivilegeRepository;
import edu.sru.group3.WebBasedEvaluations.repository.RoleRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;
import edu.sru.group3.WebBasedEvaluations.service.AdminMethodsService;

@Controller
public class AddRolesController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private EvaluatorRepository evaluatorRepo;
	@Autowired
	private DepartmentRepository deptRepo;
	@Autowired
	private LocationRepository locRepo;
	@Autowired
	private LocationGroupRepository locGroupRepo;
	@Autowired
	private PrivilegeRepository privRepo;
	@Autowired
	private EvaluationRepository evalFormRepo;
	
	@Autowired
	private AdminMethodsService adminMethodsService;
	
	
	private final String TEMP_FILES_PATH = "src\\main\\resources\\temp\\";
	private Logger log = LoggerFactory.getLogger(EvalFormController.class);
	
	
	
	
	public AddRolesController() {
		
	}


	@GetMapping("/admin_roles")
	public String addRoles(Model model, Authentication auth) {


		MyUserDetails userD = (MyUserDetails) auth.getPrincipal();
		User currentUser = userRepo.findByid(userD.getID());

		Company currentCompany = (currentUser.getCompany());


		// Create the directories if they do not exist, delete any existing files
		try {
			Files.createDirectories(Paths.get(TEMP_FILES_PATH));
			FileUtils.cleanDirectory(new File(TEMP_FILES_PATH));
		} catch (IOException e1) {
			e1.printStackTrace();
			log.error("Directory '" + TEMP_FILES_PATH + "' could not be created or cleaned.");
		}

		
		List<Role> roles = roleRepo.findByCompany(currentCompany);
		
		
		model.addAttribute("roles", roles);
		model = AdminMethodsService.pageNavbarPermissions(currentUser, model, evaluatorRepo,evalFormRepo);
		
		return "/admin_roles";
	}
	
	
	
	@RequestMapping(value = "/upload_roles", method = RequestMethod.POST)
	public Object uploadRoles(@RequestParam("file") MultipartFile file, RedirectAttributes redir, Authentication auth) throws Exception {
		
		MyUserDetails userD = (MyUserDetails) auth.getPrincipal();
		User currentUser = userRepo.findByid(userD.getID());

		Company currentCompany = (currentUser.getCompany());
		
		XSSFSheet sheet = null;
		String mess ="";
		String ansr = "";
		boolean success = false;
		
		
		if(!currentUser.isSuperUser() && !currentUser.isCompanySuperUser()) {
			RedirectView redirectView = new RedirectView("/admin_roles", true);
			redir.addFlashAttribute("error", "User must be a company admin or a super user to add roles");
			return redirectView;
		}
		
		
		try {
			sheet = ExcelRead_group.loadFile(file).getSheetAt(0);
			//check the "filetype"
			String name = ExcelRead_group.checkStringType(sheet.getRow(0).getCell(13));
			if(!name.equals("Role Template")) {
				throw new Exception("Invalid file");
			}

		} catch (Exception e) {				
			RedirectView redirectView = new RedirectView("/admin_roles", true);
			redir.addFlashAttribute("error", "invalid file");
			return redirectView;			
		}
		
		
		
		
		Department dept = null;
		Location loc = null;
		boolean locRead = false;
		boolean locWrite = false;
		boolean locDelete = false;
		boolean locEditEvaluator = false;
		boolean deptRead = false;
		boolean deptWrite = false;
		boolean deptDelete = false;
		boolean deptEditEvaluator = false;
		String deptName = "";
		String locName = "";
		boolean hasLoc = false;
		boolean hasDept = false;
		boolean assignedLoc = false;
		boolean assignedDept = false;
		
//		HashSet<Role> newRoles = new HashSet<Role>();
//		HashSet<Privilege> newPrivs= new HashSet<Privilege>();
		
		
		for (int i = 1; sheet.getRow(i) != null; i++) {
			try {
				String roleName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(0));
				Role currentRole = roleRepo.findByNameAndCompany(roleName, currentCompany);
				hasLoc = false;
				hasDept = false;
				
				if(currentRole == null) {
					currentRole = new Role(roleName, currentCompany);
//					newRoles.add(currentRole);
				}
				
				
				deptName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(1));
				
				if(deptName == null || deptName.length() == 0) {
					hasDept = false;
				}
				else {					
					hasDept = true;
					//department
					dept = deptRepo.findByNameAndCompany(deptName, currentCompany);
					
					
					if(dept == null) {
						RedirectView redirectView = new RedirectView("/admin_roles", true);
						redir.addFlashAttribute("error", "dept " + deptName + " does not exists, please ensure it is created before associating it with a role.");
						return redirectView;	
					}
					
					//permissions for that dept
					deptRead = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(2));
					deptWrite = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(3));
					deptDelete = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(4));
					deptEditEvaluator = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(5));
				}
				
				
				locName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(6));
				
				if(locName == null || locName.length() == 0) {
					hasLoc = false;
				}
				else {
					
					hasLoc = true;
					//location
					loc = locRepo.findByLocationNameAndCompany(ExcelRead_group.checkStringType(sheet.getRow(i).getCell(6)), currentCompany);				

					if(loc == null) {
						RedirectView redirectView = new RedirectView("/admin_roles", true);
						redir.addFlashAttribute("error", "location " + locName + " does not exists, please ensure it is created before associating it with a role.");
						return redirectView;	
					}
					
					//permissions for that location
					locRead = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(7));
					locWrite = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(8));
					locDelete = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(9));
					locEditEvaluator = ExcelRead_group.checkBooleanType(sheet.getRow(i).getCell(10));
				}
				
				
				
				
				//checks to see if there is a privilege that already contained the right read/write/delete/editevaluator perms that could be added to. 
				assignedLoc = false;
				assignedDept = false;
				for(Privilege priv : currentRole.getPrivileges()) {
					System.out.println(priv.getName() + (hasLoc && !assignedLoc));
					if(hasLoc && !assignedLoc) {						
						if(priv.getRead() == locRead && priv.getWrite() == locWrite && priv.getDelete() == locDelete && priv.getEditEvaluator() == locEditEvaluator && priv.containsLoc(loc)) {
							assignedLoc = true;
							break;						
						}
						else if(priv.getRead() == locRead && priv.getWrite() == locWrite && priv.getDelete() == locDelete && priv.getEditEvaluator() == locEditEvaluator) {
							assignedLoc = true;
							priv.addLocGroup(new LocationGroup(loc.getParentCity().getCityName(),loc,currentCompany));
//							privRepo.save(priv);
//							currentRole.addPrivilege(priv);
						}						
					}
					System.out.println(priv.getName() + (hasDept && !assignedDept));
					if(hasDept && !assignedDept) {						
						if(priv.getRead() == deptRead && priv.getWrite() == deptWrite && priv.getDelete() == deptDelete && priv.getEditEvaluator() == deptEditEvaluator && priv.getDepts().contains(dept)){
							assignedDept = true;
							break;						
						}
						else if(priv.getRead() == deptRead && priv.getWrite() == deptWrite && priv.getDelete() == deptDelete && priv.getEditEvaluator() == deptEditEvaluator) {
							assignedDept = true;
							priv.addDept(dept);
//							privRepo.save(priv);
//							currentRole.addPrivilege(priv);
						}						
					}						
						
					
				}
				
				if(hasLoc && hasDept && !assignedLoc && !assignedDept && locRead == deptRead &&  locWrite == deptWrite && locDelete == deptDelete && locEditEvaluator == deptEditEvaluator) {
					System.out.println(currentRole.getName() + " creating both");
					String name = this.privName(currentCompany, loc, locRead, locWrite, locDelete, locEditEvaluator);
					Privilege priv = new Privilege(name,currentRole,locRead, locWrite, locDelete, locEditEvaluator);					
					priv.addLocGroup(new LocationGroup(loc.getParentCity().getCityName(),loc,currentCompany));
					priv.addDept(dept);
					currentRole.addPrivilege(priv);
//					newPrivs.add(priv);
					assignedLoc = true;
					assignedDept = true;
					
				}
				if(hasLoc && !assignedLoc) {
					System.out.println(currentRole.getName() + " creating loc");
					String name = this.privName(currentCompany, loc, locRead, locWrite, locDelete, locEditEvaluator);
					Privilege priv = new Privilege(name,currentRole,locRead, locWrite, locDelete, locEditEvaluator);
					priv.addLocGroup(new LocationGroup(loc.getParentCity().getCityName(),loc,currentCompany));
					currentRole.addPrivilege(priv);
//					newPrivs.add(priv);
				}					
				if(hasDept && !assignedDept) {
					System.out.println(currentRole.getName() + " creating dept");
					String name = this.privName(currentCompany, dept, deptRead, deptWrite, deptDelete, deptEditEvaluator);
					Privilege priv = new Privilege(name, currentRole, deptRead, deptWrite, deptDelete, deptEditEvaluator);
					priv.addDept(dept);
					currentRole.addPrivilege(priv);
//					newPrivs.add(priv);
				}
				
				try {
					roleRepo.save(currentRole);
					success = true;
				}
				catch(Exception e) {
					e.printStackTrace();
					success = false;	
					
					RedirectView redirectView = new RedirectView("/admin_roles", true);
					redir.addFlashAttribute("error", "error saving role: " + currentRole.getName());
					return redirectView;
				}
			}
			catch(Exception e) {
				log.error("Could not add role in row: " + (sheet.getRow(i).getRowNum() + 1) + " from "
						+ file.getOriginalFilename() + "\n" + e.getMessage());
				redir.addFlashAttribute("log", "error");
			}	
			
		}
		
		
		
		
		
		
		
		
		if(success) {
			mess = "File uploaded! Role(s) successfully added!";
			ansr = "addPass";
			log.info(file.getOriginalFilename() + " roles added successfuly");
			redir.addFlashAttribute("mess", mess);
			redir.addFlashAttribute("ansr", ansr);
		}
		else
		{
			mess = "File failed to be uploaded!";
			ansr = "addFail";
			log.error("error saving roles from file " + file.getOriginalFilename());
			redir.addFlashAttribute("mess", mess);
			redir.addFlashAttribute("ansr", ansr);
		}
		
		
		
		
		redir.addFlashAttribute("completed","File uploaded");
		
		RedirectView redirectView = new RedirectView("/admin_roles", true);
		
		return redirectView;
	}
	
	/**
	 * @param co company to add name of
	 * @param loc to add name of
	 * @param read permission of priv
	 * @param write permission of priv
	 * @param delete permission of priv
	 * @param editEvaluator permission of priv
	 * @return the unique name of the privilege
	 */
	public String privName(Company co, Location loc, boolean read, boolean write, boolean delete, boolean editEvaluator) {
		String name = "";
		
		name += co.getCompanyName() + "_";
		name += loc.getLocationName() + "_";
		
		if(read) {
			name += "r";
		}
		else {
			name += "-";
		}
		
		if(write) {
			name += "w";
		}
		else {
			name += "-";
		}
		
		if(delete) {
			name += "d";
		}
		else {
			name += "-";
		}
		
		if(editEvaluator) {
			name += "e";
		}
		else {
			name += "-";
		}
		
		
		
		return name;
	}
	
	/**
	 * @param co company to add name of
	 * @param dept to add name of
	 * @param read permission of priv
	 * @param write permission of priv
	 * @param delete permission of priv
	 * @param editEvaluator permission of priv
	 * @return the unique name of the privilege
	 */
	public String privName(Company co, Department dept, boolean read, boolean write, boolean delete, boolean editEvaluator) {
		String name = "";
		name += co.getCompanyName() + "_";
		name += dept.getName() + "_";
		
		if(read) {
			name += "r";
		}
		else {
			name += "-";
		}
		
		if(write) {
			name += "w";
		}
		else {
			name += "-";
		}
		
		if(delete) {
			name += "d";
		}
		else {
			name += "-";
		}
		
		if(editEvaluator) {
			name += "e";
		}
		else {
			name += "-";
		}
		
		
		return name;
	}
	
	
}
