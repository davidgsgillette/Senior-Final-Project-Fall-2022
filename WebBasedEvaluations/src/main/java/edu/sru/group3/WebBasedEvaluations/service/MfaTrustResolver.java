package edu.sru.group3.WebBasedEvaluations.service;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class MfaTrustResolver implements AuthenticationTrustResolver {
	
	private final AuthenticationTrustResolver delegate = new AuthenticationTrustResolverImpl();

	@Override
	public boolean isAnonymous(Authentication authentication) {
		return this.delegate.isAnonymous(authentication);
		
	}

	@Override
	public boolean isRememberMe(Authentication authentication) {
		return this.delegate.isRememberMe(authentication);
	}

}
