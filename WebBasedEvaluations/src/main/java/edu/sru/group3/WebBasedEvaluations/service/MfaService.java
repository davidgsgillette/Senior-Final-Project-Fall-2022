package edu.sru.group3.WebBasedEvaluations.service;

import java.security.GeneralSecurityException;
import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import org.springframework.stereotype.Service;

public class MfaService {

	public boolean check(String hexKey, String code) {
		try {
			return TimeBasedOneTimePasswordUtil.validateCurrentNumberHex(hexKey, Integer.parseInt(code), 1000);
		}
		catch (GeneralSecurityException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
