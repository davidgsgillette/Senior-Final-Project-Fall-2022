package edu.sru.group3.WebBasedEvaluations.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.sru.group3.WebBasedEvaluations.domain.PasswordResetToken;
import edu.sru.group3.WebBasedEvaluations.domain.ResetPassword;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.repository.PasswordTokenRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;
import edu.sru.group3.WebBasedEvaluations.service.AdminMethodsService;
import edu.sru.group3.WebBasedEvaluations.service.EmailSenderService;

public class TwoFactorLoginController {
	
	@Autowired
	private EmailSenderService service;
	@Autowired
	private PasswordTokenRepository passwordTokenRepository;
	@Autowired
	private UserRepository userRepository;
	
	public String loginRequest(@ModelAttribute ResetPassword login, Model model) {
	String token = UUID.randomUUID().toString();
	String path = "http://localhost:8080";
	String url = path + "/loginRequest?token=" + token;
	String ansr;
	String mess;
	System.out.println(login.getEmail());
	System.out.println(login.getPassword());

	// LocalDate localDate = LocalDate.now();
	model.addAttribute("reset", login);
	try {
		ansr = "pass";
		mess = "Email sent";

		User user = userRepository.findByEmail(login.getEmail());

		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);

		passwordResetToken.setUserIdReset(user.getId());

		passwordTokenRepository.save(passwordResetToken);

		service.sendSimpleEmail(login.getEmail(),
				"The reset token associated with this url will expire in 10 minutes. \n" + url, "Password Reset");

		model.addAttribute("ansr", ansr);
		model.addAttribute("mess", mess);

		emailSentConfirm(model);

	} catch (Exception e) {
		mess = "Email could not be sent";

		ansr = "fail";

	}
	model.addAttribute("mess", mess);

	model.addAttribute("ansr", ansr);

	return "login";
	}
	
	@GetMapping("/email_sent")
	public void emailSentConfirm(Model model) {
		String ansr = "Email has been sent";
		String succ = "test";

		model.addAttribute("test", succ);
		model.addAttribute("mess", ansr);

		model.addAttribute("ansr", ansr);
	}

}
