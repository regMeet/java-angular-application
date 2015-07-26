package com.company.project.webservice.interfaces;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.company.project.VO.AuthEntityVO;
import com.company.project.VO.SatellizerPayloadVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpStatusException;
import com.company.project.persistence.entities.User;
import com.nimbusds.jose.JOSEException;

public interface AuthRestService {

	public abstract ResponseEntity login(User user, HttpServletRequest request) throws JOSEException;

	public abstract ResponseEntity signup(User user, HttpServletRequest request) throws JOSEException;

	public abstract AuthEntityVO loginFacebook(SatellizerPayloadVO payload, HttpServletRequest request) throws HttpStatusException;

	public abstract AuthEntityVO loginGoogle(SatellizerPayloadVO payload, HttpServletRequest request) throws HttpStatusException;

	public abstract ResponseEntity unlink(String provider, HttpServletRequest request) throws ParseException, IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException, JOSEException, HttpAuthenticationException;
}
