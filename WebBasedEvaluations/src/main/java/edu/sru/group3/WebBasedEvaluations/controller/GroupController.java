package edu.sru.group3.WebBasedEvaluations.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SerializationUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import edu.sru.group3.WebBasedEvaluations.domain.EvaluationLog;
import edu.sru.group3.WebBasedEvaluations.domain.Evaluator;
import edu.sru.group3.WebBasedEvaluations.domain.EvaluatorId;
import edu.sru.group3.WebBasedEvaluations.domain.Group;
import edu.sru.group3.WebBasedEvaluations.domain.MyUserDetails;
import edu.sru.group3.WebBasedEvaluations.domain.Reviewee;
import edu.sru.group3.WebBasedEvaluations.domain.SelfEvaluation;
import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.domain.Archive;
import edu.sru.group3.WebBasedEvaluations.domain.EvalRole;
import edu.sru.group3.WebBasedEvaluations.domain.EvalTemplates;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.evalform.Evaluation;
import edu.sru.group3.WebBasedEvaluations.evalform.GenerateEvalReport;
import edu.sru.group3.WebBasedEvaluations.evalform.GenerateEvalReportPoi;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationLogRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.RevieweeRepository;
import edu.sru.group3.WebBasedEvaluations.repository.ArchiveRepository;
import edu.sru.group3.WebBasedEvaluations.repository.CompanyRepository;
import edu.sru.group3.WebBasedEvaluations.repository.DepartmentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvalRoleRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;
import edu.sru.group3.WebBasedEvaluations.service.AdminMethodsService;
import edu.sru.group3.WebBasedEvaluations.repository.GroupRepository;
import edu.sru.group3.WebBasedEvaluations.excel.ExcelRead_group;

/**
 * Group controller determines the group behavior in the application
 *
 */
@Controller
public class GroupController {

	private Logger log = LoggerFactory.getLogger(GroupController.class);
	private GroupRepository groupRepository;

	private UserRepository userRepository;

	@Autowired
	private DepartmentRepository deptRepo;
	
	private EvaluatorRepository evaluatorRepository;
	private EvaluationLogRepository evaluationLogRepository;
	private RevieweeRepository revieweeRepository;
	private EvalRoleRepository evalRoleRepository;
	private EvaluationRepository evaluationRepository;
	private EvaluationRepository evalFormRepo;
	private ArchiveRepository archiveRepository ;
	private CompanyRepository companyRepo;
	private UserRepository	userRepo;

	private final String TEMP_FILES_PATH = "src\\main\\resources\\temp\\";

	public GroupController(GroupRepository groupRepository, UserRepository userRepository,
			EvaluatorRepository evaluatorRepository, RevieweeRepository revieweeRepository,
			EvaluationLogRepository evaluationLogRepository, EvalRoleRepository roleRepository,
			EvaluationRepository evaluationRepository, EvaluationRepository evalFormRepo,
			ArchiveRepository archiveRepository, CompanyRepository companyRepo, UserRepository	userRepo
			) {
		this.evaluatorRepository = evaluatorRepository;
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.revieweeRepository = revieweeRepository;
		this.evaluationLogRepository = evaluationLogRepository;
		this.evalRoleRepository = roleRepository;
		this.evaluationRepository = evaluationRepository;
		this.evalFormRepo = evalFormRepo;
		this.archiveRepository=archiveRepository;
		this.companyRepo = companyRepo;
		this.userRepo = userRepo;
	}


	@RequestMapping(value = "/addgroup", method = RequestMethod.POST)
	public String addSave(@ModelAttribute("group") Group group,
			@RequestParam(value = "rev", required = false) long[] rev,
			@RequestParam(value = "lone", required = false) long lone,
			@RequestParam(value = "ltwo", required = false) long ltwo,
			@RequestParam(value = "facetoface", required = false) long facetoface, BindingResult bindingResult,
			Model model) {
		if (rev != null) {

			Reviewee reviewee = null;
			User user = null;
			for (int i = 0; i < rev.length; i++) {
				user = userRepository.findByid(rev[i]);
				reviewee = new Reviewee(group, user.getName(), user);
				group.appendReviewee(reviewee);
			}
			for (int i = 0; i < group.getReviewee().size(); i++) {

			}
			long id = group.getId();
			groupRepository.save(group);
			Company currentCompany = group.getCompany();

			Evaluator eval1 = new Evaluator(userRepository.findByid(lone), group,
					evalRoleRepository.findById(1).orElse(null),currentCompany);
			Evaluator eval2 = new Evaluator(userRepository.findByid(ltwo), group,
					evalRoleRepository.findById(1).orElse(null),currentCompany);
			Evaluator eval3 = new Evaluator(userRepository.findByid(facetoface), group,
					evalRoleRepository.findById(1).orElse(null),currentCompany);

			evaluatorRepository.save(eval1);
			evaluatorRepository.save(eval2);
			evaluatorRepository.save(eval3);
			List<Reviewee> revieweelist = revieweeRepository.findBygroup_Id(id);
			for (int a = 0; a < revieweelist.size(); a++) {

				evaluationLogRepository.save(new EvaluationLog(eval1, revieweelist.get(a)));
				evaluationLogRepository.save(new EvaluationLog(eval2, revieweelist.get(a)));
				evaluationLogRepository.save(new EvaluationLog(eval3, revieweelist.get(a)));
			}

		}

		return "home";

	}





	/**editgroup
	 * takes user to the eidt goup page where use can make changes to evaluator, reviewee and group attributes.
	 * @param redir
	 * @param id  of the group being edited 
	 * @param model model is the a model object use to add attributes to a web page 
	 * @return user to the group edit page
	 */
	@GetMapping("/editgroup/{id}")
	public Object editgroup(RedirectAttributes redir ,@PathVariable("id") long id, Model model) {

		Group group = groupRepository.findById(id);
		if(group.getEvalstart()) {
			RedirectView redirectView = new RedirectView("/admin_groups", true);
			redir.addFlashAttribute("error", "Can not  edit group "+id +", evaluation have started");
			return redirectView;
		}
		model.addAttribute("group", group);
		List<Boolean> synclist = new ArrayList<Boolean>();
		List<Boolean> prevlist = new ArrayList<Boolean>();
		List<Evaluator> evallist = evaluatorRepository.findByGroupId(id);

		List<EvalRole> roles = (List<EvalRole>) evalRoleRepository.findAll();
		List<Reviewee> rev = revieweeRepository.findBygroup_Id(id);
		List<Long> revid = new ArrayList<Long>();
		for (int x = 0; x < rev.size(); x++) {
			revid.add(rev.get(x).getUser().getId());
		}
		Map<EvalRole, List<Long>> evals = new LinkedHashMap<EvalRole, List<Long>>();

		for (int x = 0; x < roles.size(); x++) {

			evals.put(roles.get(x), null);
			List<Long> temp = new ArrayList<Long>();
			for (int y = 0; y < evallist.size(); y++) {

				if (roles.get(x).getId() == evallist.get(y).getLevel().getId()) {
					temp.add((long) evallist.get(y).getUser().getId());

				}

			}
			evals.put(roles.get(x), temp);
		}
		for (int x = 0; x < roles.size(); x++) {
			int size = synclist.size();
			for (int y = 0; y < evallist.size(); y++) {

				if (roles.get(x).getId() == evallist.get(y).getLevel().getId()) {

					synclist.add(evallist.get(y).isSync());

					break;
				}
			}
			if(size == synclist.size()) {
				synclist.add(false);
			}

		}
		for (int x = 0; x < roles.size(); x++) {
			int size = prevlist.size();
			for (int y = 0; y < evallist.size(); y++) {

				if (roles.get(x).getId() == evallist.get(y).getLevel().getId()) {

					prevlist.add(evallist.get(y).isPreview());

					break;
				}
			}
			if(size == prevlist.size()) {
				prevlist.add(false);
			}


		}
		synclist.add(false);
		EvalRole temprole = roles.get(0);
		//System.out.println(evals.get(temprole));
		//System.out.println(evallist.get(0).getId());
		model.addAttribute("evallist", evals);
		model.addAttribute("sync", synclist);
		model.addAttribute("prev", prevlist);
		model.addAttribute("revedit", revid);
		//need to add new role in here. 
		//		model.addAttribute("users", userRepository.findByRoleName("USER","EVALUATOR_EVAL",Sort.by(Sort.Direction.ASC, "lastName")));
		//		model.addAttribute("usersEval", userRepository.findByRoleName("EVALUATOR","EVALUATOR_EVAL",Sort.by(Sort.Direction.ASC, "lastName")));
		model.addAttribute("rolles", roles);
		model.addAttribute("forms", evalFormRepo.findAll());
		log.info("group_edit open");
		return "group_edit";

	}


	/**update group 
	 * save changes to the group 
	 * @param id of the group being edited 
	 * @param rev return a new list of reviewee
	 * @param eval list of evaluators 
	 * @param roles list of roles
	 * @param issync determines if a evaluator is issync or not 
	 * @param isprev is the evaluation able to be previewed 
	 * @param form what form is associated  with the group
	 * @param self  is self evaluation needed 
	 * @param model
	 * @return  user back to the edit group page 
	 */
	@RequestMapping(value = "/updategroup{id}", method = RequestMethod.POST)
	public String update(@PathVariable("id") long id, @RequestParam(value = "rev", required = false) long[] rev,
			@RequestParam(value = "eval", required = false) long[] eval,
			@RequestParam(value = "role", required = false) int[] roles,
			@RequestParam(value = "issync", required = false) boolean[] issync,
			@RequestParam(value = "isprev", required = false) boolean[] isprev,
			@RequestParam(value = "form", required = false) EvalTemplates form,
			@RequestParam(value = "selfeval", required = false) boolean self, Model model, Authentication auth) {

		//System.out.println("start");
		//for (int x = 0; x < isprev.length; x++) {
		//	System.out.println(isprev[x]);
		//}
		//System.out.println("end");
		//System.out.println(isprev.length);
		//System.out.println(roles.length);
		User currentUser = userRepository.findByid(((MyUserDetails) auth.getPrincipal()).getID());
		//  edits group 
		Group group = groupRepository.findById(id);
		Company currentCompany = group.getCompany();

		group.getReviewee().clear();
		group.getEvaluator().clear();
		Reviewee reviewee = null;
		User user = null;
		for (int i = 0; i < rev.length; i++) {
			user = userRepository.findByid(rev[i]);
			reviewee = new Reviewee(group, user.getName(), user);
			group.appendReviewee(reviewee);
		}
		//for (int i = 0; i < group.getReviewee().size(); i++) {
		//	System.out.println(group.getReviewee().get(i).getName());
		//}
		group.setSelfeval(self);
		group.setEvalTemplates(form);
		groupRepository.save(group);
		group = groupRepository.findById(id);


		List<Reviewee> revieweelist = revieweeRepository.findBygroup_Id(id);





		for (int i = 0; i < roles.length; i++) {
			Evaluator temp = new Evaluator(userRepository.findByid(eval[i]), group,
					evalRoleRepository.findById(roles[i]).orElse(null),currentCompany);
			temp.setSync(issync[roles[i] - 1]);
			temp.setPreview(isprev[roles[i] - 1]);
			List<Evaluator> eval2 = (evaluatorRepository.findByLevelLevelAndGroupId(roles[i] - 1, group.getId()));
			List<Evaluator> eval3 = (evaluatorRepository.findByLevelLevelAndGroupId(roles[i] + 1, group.getId()));
			for (int a = 0; a < revieweelist.size(); a++) {
				EvaluationLog eltemp = new EvaluationLog(temp, revieweelist.get(a));

				for (int k = 0; k < eval2.size(); k++) {
					EvaluationLog log1 = evaluationLogRepository.findByEvaluatorIdAndRevieweeId(eval2.get(k).getId(),
							revieweelist.get(a).getId());
					if ((eval2.get(k).isSync() != true) && log1.getAuth()) {
						eltemp.setAuth(true);

					} else {
						eltemp.setAuth(false);

					}
				}
				temp.appendEvalutationLog(eltemp);
				for (int k = 0; k < eval3.size(); k++) {
					EvaluationLog log2 = evaluationLogRepository.findByEvaluatorIdAndRevieweeId(eval3.get(k).getId(),
							revieweelist.get(a).getId());
					if ((temp.isSync() != true) && eltemp.getAuth()) {
						log2.setAuth(true);

						evaluationLogRepository.save(log2);

					}

				}



			}
			evaluatorRepository.save(temp);
		}


		return "redirect:/editgroup/" + id;

	}

	/**uploadgroup
	 * takes an excel file and takes the data from it and creates groups 
	 * @param reapExcelDataFile read the excel file 
	 * @param redir hold redirection attributes 
	 * 
	 * @return admingroup page 
	 */
	@RequestMapping(value = "/uploadgroup", method = RequestMethod.POST)
	public Object uploadgroup(@RequestParam("file") MultipartFile reapExcelDataFile, RedirectAttributes redir, Authentication auth) {

		User currentUser = userRepository.findByid(((MyUserDetails) auth.getPrincipal()).getID());
		Company currentCompany = currentUser.getCompany();
		//checks if user can assign evaluator. 
		if(!(currentUser.isCompanySuperUser() || currentUser.hasEditEvalPerm())) {
			RedirectView redirectView = new RedirectView("/admin_groups", true);
			redir.addFlashAttribute("error", "invalid permissions on user "+currentUser.getName());
			log.error("invalid permissions on user "+currentUser.getName()+ " does not have permission to create a eval group or assign evaluator role.");
			return redirectView;
		}
		//if(currentUser.isCompanySuperUser() || currentUser.getRole().evalableUsers().contains(something))

		XSSFSheet sheet = null;
		XSSFSheet sheet2 = null;
		XSSFSheet sheet3 = null;
		List<Group> grouplist = new ArrayList<Group>();
		List<Evaluator> evaluatorlist = new ArrayList<Evaluator>();
		List<EvalRole> rolelist = new ArrayList<EvalRole>();
		Map<Integer, List<String>> syncmap = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>>  previewmap = new HashMap<Integer, List<String>>();
		try {
			sheet = ExcelRead_group.loadFile(reapExcelDataFile).getSheetAt(0);
			sheet2 = ExcelRead_group.loadFile(reapExcelDataFile).getSheetAt(1);
			sheet3 = ExcelRead_group.loadFile(reapExcelDataFile).getSheetAt(2);
		} catch (Exception e) {

			RedirectView redirectView = new RedirectView("/admin_groups", true);
			redir.addFlashAttribute("error", "invalid file");
			return redirectView;
		}
		String type = ExcelRead_group.checkStringType(sheet3.getRow(0).getCell(1));
		if (!type.equals("Groups")) {
			RedirectView redirectView = new RedirectView("/admin_groups", true);
			redir.addFlashAttribute("error", "wrong file type");
			//System.out.println(ExcelRead_group.checkStringType(sheet3.getRow(0).getCell(1)));
			return redirectView;
		}

		// roles
		for (int i = 1; sheet3.getRow(i) != null; i++) {
			int level = ExcelRead_group.checkIntType(sheet3.getRow(i).getCell(1));
			String evalRoleName = ExcelRead_group.checkStringType(sheet3.getRow(i).getCell(0));	
			String companyName = ExcelRead_group.checkStringType(sheet3.getRow(i).getCell(2));
			Company co = companyRepo.findByCompanyName(companyName);
			if(co == null) {
				RedirectView redirectView = new RedirectView("/admin_groups", true);
				redir.addFlashAttribute("error", "company " + companyName + "does not exist, cannot add it to a group.");
				log.error("company " + companyName + "does not exist, cannot add it to a group.");
				return redirectView;
			}
			else if(!co.equals(currentCompany)) {
				RedirectView redirectView = new RedirectView("/admin_groups", true);
				redir.addFlashAttribute("error", "User with company access to  " + currentCompany.getCompanyName() + " doesnt have access to " + co.getCompanyName());
				log.error("User with company access to  " + currentCompany.getCompanyName() + " doesnt have access to " + co.getCompanyName());
				return redirectView;
			}
			EvalRole evalRole = evalRoleRepository.findByNameAndCompany(evalRoleName,currentUser.getCompany());

			if(evalRole != null && evalRole.getCompany().getCompanyName().equals(currentUser.getCompanyName())) {
//				RedirectView redirectView = new RedirectView("/admin_groups", true);
//				redir.addFlashAttribute("error", "Eval Role with Name " + evalRoleName + " already exists");
//				log.error("Eval Role with Name " + evalRoleName + " already exists");
//				return redirectView;
				rolelist.add(evalRole);
			}
			else {
				//took this out role,
				rolelist.add(new EvalRole(evalRoleName,level, currentCompany));
				//System.out.println(roll + " " + roll_name);
			}
			

		}
		int totalrole = rolelist.size();
		evalRoleRepository.saveAll(rolelist);
		// groups
		for (int i = 0; sheet.getRow(0).getCell(i) != null; i++) {
			List<String> synclist = new ArrayList<String>();
			List<String> previewlist = new ArrayList<String>();
			
			Group group = new Group(currentUser.getCompany());

			// long id = (Long) null;

			for (int x = 0; sheet.getRow(x) != null; x++) {
				// System.out.print(sheet.getRow(x).getCell(i));
				if (x == 0) {
					String groupstringid = ExcelRead_group.checkStringType(sheet.getRow(x).getCell(i))
							.replaceAll("\\s", "").replace("Group", "");
					group.setGroupNum(Integer.parseInt(groupstringid));
					Group tempGroup = this.groupRepository.findByNumberAndCompany(Integer.parseInt(groupstringid), currentCompany);
					if(tempGroup != null) {
						RedirectView redirectView = new RedirectView("/admin_groups", true);
						redir.addFlashAttribute("error", "group " +  Integer.parseInt(groupstringid) + " already exists.");
						//System.out.println("user doesn't not exist1 " + evaltemplateid);
						log.error("group " +  Integer.parseInt(groupstringid) + " already exists.");
						return redirectView;
					}
				}

				else if (x == 1) {
					String evaltemplateid = ExcelRead_group.checkStringType(sheet.getRow(x).getCell(i));
					EvalTemplates evaltemp = evaluationRepository.findByNameAndCompany(evaltemplateid,currentCompany);
					if (evaltemp == null) {
						RedirectView redirectView = new RedirectView("/admin_groups", true);
						redir.addFlashAttribute("error", "template " + evaltemplateid + " does not exist, please upload it before trying to create groups that use that template");
						//System.out.println("user doesn't not exist1 " + evaltemplateid);
						log.error("user does not exist " + evaltemplateid);
						return redirectView;
					}

					boolean found = true;
					for(User user : group.getUsers()) {
						for(Department dept : evaltemp.getDepts()) {
							if(!user.getDepartments().contains(dept)) {
								found = false;
							}
							else {
								found = true;
								break;
							}							
						}
						if(!found) {
							break;
						}
					}
					if(found) {
						group.setEvalTemplates(evaltemp);
					}
					else {
						RedirectView redirectView = new RedirectView("/admin_groups", true);
						redir.addFlashAttribute("error", "user not found in any of the listed departments, could not user template on group");
						//System.out.println("user doesn't not exist1 " + evaltemplateid);
						log.error("user not found in any of the listed departments, could not user template on group");
						return redirectView;
					}


					

				}
				// is self eval needed
				else if (x == 2) {
					String self = ExcelRead_group.checkStringType(sheet.getRow(x).getCell(i)).replaceAll("\\s", "");
					if (self.equals("NoSelf-Eval")) {
						group.setSelfeval(false);
					} else if (self.equals("Self-Eval")) {
						group.setSelfeval(true);
					} else {
						RedirectView redirectView = new RedirectView("/admin_groups", true);
						redir.addFlashAttribute("error", "in group  " + group.getId()+ " layout");
						log.error("error in group  " + group.getId()+ " layout");
						return redirectView;
					}

				} else if (x < (2 + totalrole)) {
					String sync = ExcelRead_group.checkStringType(sheet.getRow(x).getCell(i)).replaceAll("\\s", "");
					//System.out.print(sync);
					if(sync.equals("Sync")||sync.equals("Async")) {

						synclist.add(sync);
					}else
					{
						RedirectView redirectView = new RedirectView("/admin_groups", true);
						redir.addFlashAttribute("error", " in group " + group.getId()+ " Sync layout");
						log.error("error in group " + group.getId()+ " Sync layout");
						return redirectView;
					}

				}
				else if (x < (2 + totalrole+totalrole)) {
					String preview = ExcelRead_group.checkStringType(sheet.getRow(x).getCell(i)).replaceAll("\\s", "");
					//System.out.print(preview);
					if(preview.equals("preview")||preview.equals("nopreview")) {

						previewlist.add(preview);
					}else {
						RedirectView redirectView = new RedirectView("/admin_groups", true);
						redir.addFlashAttribute("error", "in group  " + group.getId()+ " preview layout");
						log.error("error in group  " + group.getId()+ " preview layout");
						return redirectView;
					}

				}


				else {
					if (ExcelRead_group.checkStringType(sheet.getRow(x).getCell(i)) != null) {
						String name = ExcelRead_group.checkStringType(sheet.getRow(x).getCell(i));
//						System.out.println("user/deptname:" + name);
						User user = userRepository.findByEmail(name);
						Department dept = deptRepo.findByNameAndCompany(name, currentCompany);

						//						System.out.println((currentUser.isCompanySuperUser() && currentUser.getCompanyID() == user.getCompanyID()));
						//						System.out.println(currentUser.isCompanySuperUser() );
						//						System.out.println(user.getCompanyID());
						//						System.out.println(currentUser.getCompanyID());
						//						System.out.println(currentUser.getRole().writableUsers().contains(user));
						if (user == null && dept == null) {

							redir.addFlashAttribute("error", "user or dept "
									+ name + " does not exist");

							RedirectView redirectView = new RedirectView("/admin_groups", true);
							//System.out.println("user dosnt not exist " + x + " " + i
							//		+ ExcelRead_group.checkStringType(sheet.getRow(x).getCell(i)));
							log.error("user or dept " + name + "does not not exist");
							return redirectView;
							
						} else if(user != null) {
							if(currentUser.isSuperUser() || ((currentUser.isCompanySuperUser() && currentUser.getCompanyID() == user.getCompanyID()) || currentUser.getRole().writableUsers().contains(user))) {
								Reviewee reviewee = new Reviewee(group, user.getName(), user);
								group.appendReviewee(reviewee);
							}
						}
						else if((dept != null && currentUser.isSuperUser()) || ((currentUser.isCompanySuperUser() && currentUser.getCompanyID() == dept.getCompany().getId()) || currentUser.getRole().writableDepartments().contains(dept))){
							for(User u : dept.getUsers()) {
								Reviewee reviewee = this.revieweeRepository.findByNameAndCompany(u.getName(), u.getCompany());
								if(reviewee == null) {
									reviewee = new Reviewee(group, u.getName(), u);
								}								
								group.appendReviewee(reviewee);
								reviewee = null;
							}
						}
						else {
							RedirectView redirectView = new RedirectView("/admin_groups", true);
							redir.addFlashAttribute("error", "User/dept " + currentUser.getName() + " cannot add user/dept " + name + " to a group");
							log.error("User/dept " + currentUser.getName() + " cannot add user/dept " + name + " to a group");
							return redirectView;
						}
						user = null;
						dept = null;
					}
				}

			}
			syncmap.put(group.getGroupNumber(), synclist);
			previewmap.put(group.getGroupNumber(), previewlist);
			grouplist.add(group);

		}
		try {
			currentCompany.addGroups(grouplist);
//			System.out.println("added groups to cos");
			groupRepository.saveAll(grouplist);
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			RedirectView redirectView = new RedirectView("/admin_groups", true);
			redir.addFlashAttribute("error", "problem saving grouplist");
			log.error("problem saving grouplist");
			return redirectView;
		}
		//// Evaluator
		for (int i = 0; sheet2.getRow(i) != null; i += 2) {
			User user = userRepository.findByEmail(ExcelRead_group.checkStringType(sheet2.getRow(i).getCell(0)));
			if (user != null) {			

				if(!(currentUser.isCompanySuperUser() || currentUser.getRole().evalableUsers().contains(user))) { 
					RedirectView redirectView = new RedirectView("/admin_groups", true);
					redir.addFlashAttribute("error", "User " + currentUser.getName() + " cannot give user " + user.getName() + " Evaluator permissions");
					log.error("User " + currentUser.getName() + " cannot give user " + user.getName() + " Evaluator permissions");
					return redirectView;

				} 
				for (int x = 1; sheet2.getRow(i).getCell(x) != null; x++) {
					String groupids = ExcelRead_group.checkStringType(sheet2.getRow(i).getCell(x));
					String level = ExcelRead_group.checkStringType(sheet2.getRow(i + 1).getCell(x));
					List<String> levellist = Stream.of(level.split(",")).map(String::trim).collect(Collectors.toList());
					List<String> groupNumberlist = Stream.of(groupids.split(",")).map(String::trim)
							.collect(Collectors.toList());
					int size = rolelist.size();

					for (int y = 0; y < groupNumberlist.size(); y++) {
						int groupNum = Integer.parseInt(groupNumberlist.get(y));
						for (int z = 0; z < levellist.size(); z++) {
							// if evaluator is sync or not 
							Evaluator eval = new Evaluator(user, groupRepository.findByNumberAndCompany(groupNum, currentCompany),
									evalRoleRepository.findByNameAndCompany(levellist.get(z), user.getCompany()),user.getCompany());
							int num = eval.getLevel().getLevel();
							
							if (num != size && (syncmap.get(groupNum).get(num - 1).equals("Async"))) {
								eval.setSync(false);
							} else if (num != size && syncmap.get(groupNum).get(num - 1).equals("Sync")) {
								eval.setSync(true);
							} else if (num == size) {
								eval.setSync(true);
							}

							if ((previewmap.get(groupNum).get(num - 1).equals("preview"))) {
								eval.setPreview(true);

							} 
							else if (previewmap.get(groupNum).get(num - 1).equals("nopreview")) {
								eval.setPreview(false);
							}

							List<Reviewee> rev = revieweeRepository.findByGroupNumberAndCompany(groupNum, currentCompany);
							List<Evaluator> eval2 = (evaluatorRepository.findByLevelLevelAndGroupNumberAndCompany(num - 1, groupNum, currentCompany));
							List<Evaluator> eval3 = (evaluatorRepository.findByLevelLevelAndGroupNumberAndCompany(num + 1, groupNum, currentCompany));
//							System.out.println(rev.size());
							for (int a = 0; a < rev.size(); a++) {
								EvaluationLog etemp = new EvaluationLog(eval, rev.get(a));


								for (int k = 0; k < eval2.size(); k++) {
									EvaluationLog log1 = evaluationLogRepository.findByEvaluatorIdAndRevieweeId(eval2.get(k).getId(), rev.get(a).getId());
									if ((eval2.get(k).isSync() != true) && log1.getAuth()) {
										etemp.setAuth(true);

									} else {
										etemp.setAuth(false);

									}
								}
								eval.appendEvalutationLog(etemp);
								for (int k = 0; k < eval3.size(); k++) {
									EvaluationLog log2 = evaluationLogRepository.findByEvaluatorIdAndRevieweeId(eval3.get(k).getId(), rev.get(a).getId());
									if ((eval.isSync() != true) && etemp.getAuth()) {
										log2.setAuth(true);

										evaluationLogRepository.save(log2);

									}

								}

							}

							evaluatorlist.add(eval);
							evaluatorRepository.save(eval);

						}
					}

				}
			}
			else {
				String name = ExcelRead_group.checkStringType(sheet2.getRow(i).getCell(0));
				groupRepository.deleteAll(grouplist);
				evalRoleRepository.deleteAll(rolelist);
				redir.addFlashAttribute("error", "user " + name + " dosnt not exist");
				RedirectView redirectView = new RedirectView("/admin_groups", true);
				return redirectView;
			}


		}


		redir.addFlashAttribute("completed", true);
		RedirectView redirectView = new RedirectView("/admin_groups", true);
		log.info("group was added ");
		return redirectView;
	}

	@GetMapping("/uploading")
	public String uploadgroup(Model model) {

		return "redirect:/admin_groups";
	}

	@GetMapping("/Evaluationgroups")
	public String evalGroups(Model model, Authentication authentication) {

		MyUserDetails userD = (MyUserDetails) authentication.getPrincipal();
		User currentUser = userRepository.findByid(userD.getID());
		List<Group> grouplist = (List<Group>) groupRepository.findByevaluatorUserId(userD.getID(),Sort.by(Sort.Direction.ASC, "Id"));
		
		grouplist = new ArrayList<Group>(new LinkedHashSet<Group>(grouplist));

		model.addAttribute("groups", grouplist);

		List<EvalRole> roles = (List<EvalRole>) evalRoleRepository.findAll();



		model = AdminMethodsService.pageNavbarPermissions(currentUser, model, evaluatorRepository, evalFormRepo);
		
		
		model.addAttribute("id", userD.getID());
		model.addAttribute("roles", roles);
		model.addAttribute("evalu", currentUser);
		model.addAttribute("groups", grouplist);
		log.info("EvaluationView was opened ");
		
		return "EvaluationView";
	}


	
	
	/**
	 * takes  an load information for the admin group age 
	 * @param model
	 * @return admin group page 
	 */
	@GetMapping("/admin_groups")
	public String Groups(Model model, Authentication auth) {
		
		MyUserDetails userD = (MyUserDetails) auth.getPrincipal();
		User currentUser = userRepo.findByid(userD.getID());
		
		Company currentCompany = (currentUser.getCompany());
		List<Group> grouplist = (List<Group>) groupRepository.findByCompany(currentCompany);
		List<EvalRole> roles = (List<EvalRole>) evalRoleRepository.findByCompany(currentCompany);
		List<EvaluationLog> evalLog = (List<EvaluationLog>) evaluationLogRepository.findByEvaluatorCompany(currentCompany);

		if(evalFormRepo.findByCompany(currentCompany).size() == 0) {
			return "redirect:/home";
		}

		model.addAttribute("evaluation", evalLog);
		model.addAttribute("roles", roles);

		List<String>warnings =new ArrayList<String>();
		for(int x=0; x<roles.size();x++) {
			for(int y=0; y<grouplist.size();y++) {
				Boolean temp = evaluatorRepository.existsBylevelAndGroup(roles.get(x),grouplist.get(y));
				if(temp ==false) {
					warnings.add("Group:"+" "+ grouplist.get(y).getNumber()+" is missing "+ roles.get(x).getName()+" Evaluator");
				}
			}
		}

		for(int y=0; y<grouplist.size();y++) {
			if(grouplist.get(y).getReviewee().isEmpty()) {
				warnings.add("Group:"+" "+ grouplist.get(y).getNumber()+" has no reviewee");
			}
		}
		model.addAttribute("warnings",warnings);
		if(grouplist.isEmpty()) {
			grouplist=null;
		}
		model.addAttribute("groups", grouplist);
		log.info("admin group was open ");
		
	
		
		model = AdminMethodsService.pageNavbarPermissions(currentUser, model, evaluatorRepository, evalFormRepo);
		
		
		
		return "admin_groups";
	}





	/**
	 * this method will delete a group and store it competed evaluation in the archive 
	 * @param id of the group being deleted
	 * @param model
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {

		Group group = groupRepository.findById(id);
		List<Archive> Archivelist = new ArrayList<Archive>();
		for(int x =0; x< group.getEvaluator().size();x++) {
			List<EvaluationLog> temp = group.getEvaluator().get(x).getEvalutationLog();
			for(int y =0; y< temp.size();y++) {
				if(temp.get(y).getCompleted()) {
					Archive temp2 = new Archive(temp.get(y));
					Archivelist.add(temp2);
				}

			}
		}
		for(int x =0; x< group.getReviewee().size();x++) {
			SelfEvaluation temp = group.getReviewee().get(x).getSelfEvaluation();
			if(temp !=null) {
				if(temp.getCompleted()) {
					Archive temp2 = new Archive(temp);
					Archivelist.add(temp2);
				}
			}

		}
		groupRepository.delete(group);
		archiveRepository.saveAll(Archivelist);
		log.info("group"+id +" was deleted ");
		return "redirect:/admin_groups";
	}



	/**
	 * Processes the request for the download of the Evaluation Results excel file for a given group.
	 * 
	 * @param groupId - ID of the Group
	 * @return ResponseEntity containing the download resource
	 * @throws Exception
	 */
	@GetMapping("/download_eval_group_results/{groupId}")
	public ResponseEntity<Resource> downloadEvalGroupResults(@PathVariable("groupId") long groupId) throws Exception {

		Group group = groupRepository.findById(groupId);
		String evalId = group.getEvalTemplates().getName();

		// Name of download file
		final String FILE_NAME = "Group " + groupId + " Evaluation Summary - " + evalId + ".xlsx";

		log.info("File '" + FILE_NAME + "' requested for download.");

		// Create the temp directory if it does not exist
		Files.createDirectories(Paths.get(TEMP_FILES_PATH));

		//Get Evaluation template
		List<EvalTemplates> evalTemps = (List<EvalTemplates>) evalFormRepo.findAll();
		byte[] evalTempByte = null;

		for (int i = 0; i<evalTemps.size();i++) {
			String name = evalTemps.get(i).getName();

			if (name.equals(evalId)) {
				evalTempByte = evalTemps.get(i).getEval();
			}
		}

		Evaluation evalTemp = (Evaluation) SerializationUtils.deserialize(evalTempByte);

		//Get Completed evaluations
		List<EvaluationLog> evalLogs = (List<EvaluationLog>) evaluationLogRepository.findAll();
		List<Evaluation> completedEvals = new ArrayList<Evaluation>();

		byte[] evalLogByte = null;

		for (int i = 0; i < evalLogs.size();i++) {
			if (evalLogs.get(i).getCompleted()) {
				evalLogByte = evalLogs.get(i).getPath();
				Evaluation completeEval = (Evaluation) SerializationUtils.deserialize(evalLogByte);

				if(completeEval.getEvalID().equals(evalId)) {

					String evalGroupNum = "";

					for (int j = 0; j < completeEval.getSection(0).getQuestionCount(); j++) {
						if (completeEval.getSection(0).getQuestion(j).getQText().equals("GROUP NO.")) {
							evalGroupNum = completeEval.getSection(0).getQuestion(j).getQResponse();
						}
					}

					if (evalGroupNum.matches(".*[0-9].*")) {
						evalGroupNum = evalGroupNum.replaceAll("\\D+","");
						if (Long.parseLong(evalGroupNum) == groupId) {
							completedEvals.add(completeEval);
						}
					}	
				}
			}
		}

		//System.out.println("FOUND " + completedEvals.size() + " COMPLETED EVALS FOR GROUP: " + groupId);
		log.info("Found " + completedEvals.size() + " completed evals for group " + groupId);

		// Create the excel report file
		//GenerateEvalReport.generateReport(evalTemp, completedEvals, TEMP_FILES_PATH, FILE_NAME);
		GenerateEvalReportPoi.generateReport(evalTemp, completedEvals, TEMP_FILES_PATH, FILE_NAME);
		//Download the file
		FileSystemResource resource = new FileSystemResource(TEMP_FILES_PATH + FILE_NAME);
		MediaType mediaType = MediaTypeFactory
				.getMediaType(resource)
				.orElse(MediaType.APPLICATION_OCTET_STREAM);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		ContentDisposition disposition = ContentDisposition
				.attachment()
				.filename(resource.getFilename())
				.build();
		headers.setContentDisposition(disposition);

		return new ResponseEntity<>(resource, headers, HttpStatus.OK);

	}
}
