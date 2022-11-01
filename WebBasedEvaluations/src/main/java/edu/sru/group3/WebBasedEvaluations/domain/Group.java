package edu.sru.group3.WebBasedEvaluations.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;

import org.springframework.lang.NonNull;

import edu.sru.group3.WebBasedEvaluations.company.Company;

/**
 * Groups class creates a group for evaluation the id is used to id identify the
 * group Class variables to state if a group evaluation have stared or if self
 * evaluation are needed Class variables to determine what Evaluation template
 * the group is using has a list of reviewee and evaluators. and able to append
 * to the lists
 *
 */
@Entity
@Table(name = "Groupeval")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int number;
	
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
	
	private Boolean evalstart;
	private Boolean selfeval;
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
	private List<Reviewee> reviewee = new ArrayList<>();

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
	private List<Evaluator> evaluator = new ArrayList<>();
	@OneToOne
	private EvalTemplates evalTemplates;

	/**
	 * default Group constructor sets evalstart false
	 */
	public Group() {

		this.evalstart = false;
	}
	
	public Group(Company co) {

		this.company = co;
		this.evalstart = false;
	}

	/**
	 * Group constructor
	 * 
	 * @param id       is the number associated with the group
	 * @param selfeval determines if self evaluation are needed
	 */
	public Group(Boolean selfeval, Company co,int groupNum) {

		this.company = co;
		this.evalstart = false;
		this.selfeval = selfeval;
		this.number = groupNum;
	}
// Delete this? Is the exact same as setReviewee
	public void setGroup(List<Reviewee> reviewee) {
		this.reviewee = reviewee;

	}

	
	
	public int getGroupNumber() {
		return number;
	}

	public void setGroupNumber(int groupNumber) {
		this.number = groupNumber;
	}


	public Long getId() {
		return id;
	}

	public void setGroupId(int groupNum) {
		this.number = groupNum;
	}

	public List<Reviewee> getReviewee() {
		return reviewee;
	}

	public void setReviewee(List<Reviewee> reviewee) {
		this.reviewee = reviewee;
	}

	public List<Evaluator> getEvaluator() {
		return evaluator;
	}

	/**
	 * appendReviewee append to the reviewee list
	 * 
	 * @param rev is the the new reviewee
	 */
	public void appendReviewee(Reviewee rev) {
		this.reviewee.add(rev);

	}

	public EvalTemplates getEvalTemplates() {
		return evalTemplates;
	}

	public void setEvalTemplates(EvalTemplates evalTemplates) {
		this.evalTemplates = evalTemplates;
	}

	public void setEvaluator(List<Evaluator> evaluator) {
		this.evaluator = evaluator;
	}

	public Boolean getEvalstart() {
		return evalstart;
	}

	public void setEvalstart(Boolean evalstart) {
		this.evalstart = evalstart;
	}

	public Boolean getSelfeval() {
		return selfeval;
	}

	public void setSelfeval(Boolean selfeval) {
		this.selfeval = selfeval;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
