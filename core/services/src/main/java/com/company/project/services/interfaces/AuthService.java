package com.company.project.services.interfaces;

import com.company.project.VO.AuthEntityVO;
import com.company.project.VO.SatellizerPayloadVO;
import com.company.project.api.exception.HttpStatusException;
import com.google.common.base.Optional;

public interface AuthService {

	public AuthEntityVO linkFacebook(SatellizerPayloadVO satellizerPayload, Optional<Long> userId, String remoteHost) throws HttpStatusException;

	public AuthEntityVO linkGoogle(SatellizerPayloadVO p, Optional<Long> userId, String remoteHost) throws HttpStatusException;

}
