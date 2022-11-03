package edu.sru.group3.WebBasedEvaluations.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import edu.sru.group3.WebBasedEvaluations.company.Company;


/**
 * Class used to hold uploaded Evaluation Templates
 * 
 * @author Logan Racer, Tanuj Rane
 */
@Entity
public class EvalTemplates {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	String name;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company", nullable = false)
    private Company company;
	
	@Lob
	byte[] eval;
	@Lob
	byte[] excelFile;
	
	// Constructors
	public EvalTemplates() {
		this.name = null;
		this.eval = null;
		this.excelFile = null;
	}
	public EvalTemplates(Company company) {
		this.name = null;
		this.eval = null;
		this.excelFile = null;
		this.company = company;
	}
	
	public EvalTemplates(String name, byte[] eval, byte[] excelFile, Company company) {
		this.name = name;
		this.eval = eval;
		this.excelFile = excelFile;
		this.company = company;
	}
	
	public EvalTemplates(String name, byte[] eval, Company company) {
		this.name = name;
		this.eval = eval;
		this.excelFile = null;
		this.company = company;
	}

	// Setters and Getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getEval() {
		return eval;
	}

	public void setEval(byte[] eval) {
		this.eval = eval;
	}
	
	public byte[] getExcelFile() {
		return this.excelFile;
	}

	public void setExcelFile(byte[] excelFile) {
		this.excelFile = excelFile;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public long getId() {
		return id;
	}
	
}
