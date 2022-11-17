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

import edu.sru.group3.WebBasedEvaluations.company.City;
import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Continent;
import edu.sru.group3.WebBasedEvaluations.company.Country;
import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.company.Location;
import edu.sru.group3.WebBasedEvaluations.company.LocationGroup;
import edu.sru.group3.WebBasedEvaluations.company.Province;
import edu.sru.group3.WebBasedEvaluations.company.World;
import edu.sru.group3.WebBasedEvaluations.domain.MyUserDetails;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.excel.ExcelRead_group;
import edu.sru.group3.WebBasedEvaluations.repository.CityRepository;
import edu.sru.group3.WebBasedEvaluations.repository.CompanyRepository;
import edu.sru.group3.WebBasedEvaluations.repository.ContinentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.CountryRepository;
import edu.sru.group3.WebBasedEvaluations.repository.DepartmentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationGroupRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.ProvinceRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;
import edu.sru.group3.WebBasedEvaluations.repository.WorldRepository;
import edu.sru.group3.WebBasedEvaluations.service.AdminMethodsService;


@Controller
public class CompanyController {



	@Autowired
	private UserRepository userRepo;
	@Autowired
	private EvaluatorRepository evaluatorRepo;
	@Autowired
	private CompanyRepository companyRepo;
	@Autowired
	private LocationRepository locationRepo;
	@Autowired
	private LocationGroupRepository locGroupRepo;
	@Autowired
	private DepartmentRepository deptRepo;
	@Autowired
	private WorldRepository worldRepo;
	@Autowired
	private ContinentRepository continentRepo;
	@Autowired
	private CountryRepository countryRepo;
	@Autowired
	private ProvinceRepository provinceRepo;
	@Autowired
	private CityRepository cityRepo;


	@Autowired
	private AdminMethodsService adminMethodsService;


	private final String TEMP_FILES_PATH = "src\\main\\resources\\temp\\";
	private Logger log = LoggerFactory.getLogger(EvalFormController.class);




	public CompanyController() {

	}


	@GetMapping("/admin_companies")
	public String addRoles(Model model, Authentication auth) {


		MyUserDetails userD = (MyUserDetails) auth.getPrincipal();
		User currentUser = userRepo.findByid(userD.getID());


		// Create the directories if they do not exist, delete any existing files
		try {
			Files.createDirectories(Paths.get(TEMP_FILES_PATH));
			FileUtils.cleanDirectory(new File(TEMP_FILES_PATH));
		} catch (IOException e1) {
			e1.printStackTrace();
			log.error("Directory '" + TEMP_FILES_PATH + "' could not be created or cleaned.");
		}


		List<Company> companies = companyRepo.findAll();





		model.addAttribute("companies", companies);
		model = AdminMethodsService.pageNavbarPermissions(currentUser, model, evaluatorRepo);
		return "/admin_companies";
	}



	@RequestMapping(value = "/upload_company", method = RequestMethod.POST)
	public Object uploadCompany(@RequestParam("file") MultipartFile file, RedirectAttributes redir, Authentication auth) throws Exception {

		MyUserDetails userD = (MyUserDetails) auth.getPrincipal();
		User currentUser = userRepo.findByid(userD.getID());


		XSSFSheet sheet = null;
		XSSFSheet sheet2 = null;
		String mess ="";
		String ansr = "";
		boolean success = false;



		if(!currentUser.isSuperUser()) {
			RedirectView redirectView = new RedirectView("/admin_companies", true);
			redir.addFlashAttribute("error", "User must be a Super User to add a company");
			return redirectView;
		}

		try {
			sheet = ExcelRead_group.loadFile(file).getSheetAt(0);
			sheet2 = ExcelRead_group.loadFile(file).getSheetAt(1);

		} catch (Exception e) {				
			RedirectView redirectView = new RedirectView("/admin_companies", true);
			redir.addFlashAttribute("error", "Invalid file");
			return redirectView;		
		}



		Company company = null;
		Location location = null;
		LocationGroup locationGroup = null;
		Department dept = null;

		World world = null;
		Continent continent = null;
		Country country = null;
		Province province = null;
		City city = null;

		String companyName = "";


		String locationName = "";
		String locationGroupName = "";
		String deptName = "";

		String worldName = "";
		String continentName = "";
		String countryName = "";
		String provinceName = "";
		String cityName = "";

		HashSet<Company> companies = new HashSet<>();
		//add the locations/company structure
		for (int i = 1; sheet.getRow(i) != null; i++) {

			try {


				companyName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(0));
				locationGroupName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(1));
				locationName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(2));				
				cityName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(3));					
				provinceName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(4));		
				countryName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(5));		
				continentName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(6));		
				worldName = ExcelRead_group.checkStringType(sheet.getRow(i).getCell(7));		


				company = companyRepo.findByCompanyName(companyName);				
				//check if company exists, if not create it. 
				if(company == null) {
					company = new Company(companyName);
					log.info("created company: " + companyName);
					companyRepo.save(company);
					
				}	

				world = worldRepo.findByName(worldName);
				if(world == null) {
					world = new World(worldName);
					log.info("created world: " + worldName);

				}
				continent = continentRepo.findByContinentName(continentName);
				if(continent == null) {
					continent = new Continent(continentName,world);
					log.info("created continent: " + continentName);
				}

				country = countryRepo.findByCountryName(countryName);
				if(country == null) {
					country = new Country(countryName,continent);
					log.info("created country: " + countryName);
				}

				province = provinceRepo.findByProvinceName(provinceName);
				if(province == null) {
					province = new Province(provinceName, country);
					log.info("created province: " + provinceName);

				}				

				city = cityRepo.findByCityName(cityName);
				if(city == null) {
					city = new City(cityName, province);
					log.info("created city: " + cityName);
				}

				location = locationRepo.findByLocationNameAndCompany(locationName, company);
				if(location == null) {
					location = new Location();
					location.setLocationName(locationName);
					location.setCompany(company);
					location.setParentCity(city);
					company.addLocation(location);
					log.info("created location: " + locationName);
				}

				locationGroup = locGroupRepo.findByCompanyAndName(company, locationGroupName);	
				if(locationGroup ==null) {
					locationGroup = new LocationGroup();
					locationGroup.setName(locationGroupName);
					locationGroup.setCompany(company);
					location.setLocGroup(locationGroup);
					log.info("added location: " + locationName + " to new location group " +locationGroupName);
				}
				else if(!locationGroup.getLocations().contains(location)){
					location.setLocGroup(locationGroup);
					log.info("added location: " + locationName + " to location group " +locationGroupName);
				}

				try {
					
					locGroupRepo.save(locationGroup);
					locationRepo.save(location);
					log.info("added/updated location " + location.getLocationName() + " to company " + companyName);
					success = true;
				}
				catch(Exception e) {
					e.printStackTrace();
					success = false;	
					log.error("Could not add company/location in row: " + (sheet.getRow(i).getRowNum() + 1) + " from "
							+ file.getOriginalFilename() + "\n" + e.getStackTrace().toString());
					RedirectView redirectView = new RedirectView("/admin_companies", true);
					redir.addFlashAttribute("error", "error saving company: " + companyName);
					return redirectView;
				}
				

			}
			catch(Exception e) {
				log.error("Could not add company/location in row: " + (sheet.getRow(i).getRowNum() + 1) + " from "
						+ file.getOriginalFilename() + "\n" + e.getMessage());
				redir.addFlashAttribute("log", "error");
			}


		}





		//add the depts and add them to locs int he process. 

		for (int i = 1; sheet2.getRow(i) != null; i++) {

			try {
				companyName = ExcelRead_group.checkStringType(sheet2.getRow(i).getCell(0));
				deptName = ExcelRead_group.checkStringType(sheet2.getRow(i).getCell(1));
				locationName = ExcelRead_group.checkStringType(sheet2.getRow(i).getCell(2));
				
				
				
				company = companyRepo.findByCompanyName(companyName);				
				//check if company exists, if not create it. 
				if(company == null) {
					throw new Exception("please create company " + companyName + " before adding a dept to it.");
				}					
				
				location = locationRepo.findByLocationNameAndCompany(locationName, company);
				if(location == null) {
					throw new Exception("please create location " + locationName + " before adding it to a dept.");
				}
				
				dept = deptRepo.findByNameAndCompany(deptName, company);
				if(dept == null) {
					dept = new Department();
					dept.setName(deptName);
					dept.setCompany(company);
					dept.addLocation(location);
					log.info("added dept " + deptName + " in company " + companyName);
					log.info("added location " + locationName + "to dept " + deptName + " in company " + companyName);
				}
				
				
				if(!dept.getLocations().contains(location)) {
					dept.addLocation(location);
				}
				
				if(!company.getDepartments().contains(dept)) {
					company.addDepartment(dept);
				}
				
				
				try {
					deptRepo.save(dept);
					locationRepo.save(location);
					log.info("added location " + location.getLocationName() + " to dept " + dept.getName() + " in company " + companyName);
					success = true;
				}
				catch(Exception e) {
					e.printStackTrace();
					success = false;	
					log.error("Could not add dept in row: " + (sheet.getRow(i).getRowNum() + 1) + " to company " + companyName + " in file "
							+ file.getOriginalFilename() + "\n" + e.getStackTrace().toString());
					RedirectView redirectView = new RedirectView("/admin_companies", true);
					redir.addFlashAttribute("error", "error saving company: " + companyName);
					return redirectView;
				}				


			}
			catch(Exception e) {
				success = false;
				log.error("Could not add dept/company relationship in row: " + (sheet.getRow(i).getRowNum() + 1) + " from "
						+ file.getOriginalFilename() + " sheet 2\n" + e.getMessage());
				redir.addFlashAttribute("log", "error");
				mess = "File failed to be uploaded!";
				ansr = "addFail";
				log.error("error saving Company from file " + file.getOriginalFilename());
				redir.addFlashAttribute("mess", mess);
				redir.addFlashAttribute("ansr", ansr);
				RedirectView redirectView = new RedirectView("/admin_companies", true);

				return redirectView;
			}

		}
		if(success) {
			mess = "File uploaded! Company successfully added!";
			ansr = "addPass";
			log.info(file.getOriginalFilename() + " Company added successfuly");
			redir.addFlashAttribute("mess", mess);
			redir.addFlashAttribute("ansr", ansr);
		}
		else
		{
			mess = "File failed to be uploaded!";
			ansr = "addFail";
			log.error("error saving Company from file " + file.getOriginalFilename());
			redir.addFlashAttribute("mess", mess);
			redir.addFlashAttribute("ansr", ansr);
		}




		

		RedirectView redirectView = new RedirectView("/admin_companies", true);

		return redirectView;
	}


}
