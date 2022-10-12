package edu.sru.group3.WebBasedEvaluations.controller;

import dev.samstevens.totp.exceptions.QrGenerationException;

public interface MFATokenManager {
	public String generateSecretKey();
	public String getQRCode(final String secret) throws QrGenerationException;
	public boolean verifyTotp(final String code, final String secret);

}
