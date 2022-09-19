package edu.sru.group3.WebBasedEvaluations.controller;

import java.util.UUID;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.sru.group3.WebBasedEvaluations.domain.PasswordResetToken;
import edu.sru.group3.WebBasedEvaluations.domain.ResetPassword;
import edu.sru.group3.WebBasedEvaluations.domain.User;

public class TwoFactorLoginController {
	public String loginRequest(@ModelAttribute ResetPassword reset, Model model) {
	String token = UUID.randomUUID().toString();
	String path = "http://localhost:8080";
	String url = path + "/loginRequest?token=" + token;
	String ansr;
	String mess;
	System.out.println(reset.getEmail());
	System.out.println(reset.getPassword());

	// LocalDate localDate = LocalDate.now();
	model.addAttribute("reset", reset);
	try {
		ansr = "pass";
		mess = "Email sent";

		User user = userRepository.findByEmail(reset.getEmail());

		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);

		passwordResetToken.setUserIdReset(user.getId());

		passwordTokenRepository.save(passwordResetToken);

		service.sendSimpleEmail(reset.getEmail(),
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

}
