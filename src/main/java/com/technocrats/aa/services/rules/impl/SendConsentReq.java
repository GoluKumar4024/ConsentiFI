package com.technocrats.aa.services.rules.impl;

import com.technocrats.aa.dtos.ConsentHandleStatus;
import com.technocrats.aa.dtos.ConsentResp;
import com.technocrats.aa.dtos.ErrorInfo;
import com.technocrats.aa.model.ConsentRequestDetail;
import com.technocrats.aa.services.AAClientSvc;
import com.technocrats.aa.services.rules.IProcessConsentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendConsentReq implements IProcessConsentRequest {

    private final AAClientSvc aaClientSvc;

    @Override
    public Integer getExecutionSeq() {
        return 2;
    }

    @Override
    public Boolean execute(ConsentRequestDetail consentRequestDetail) {
        try {
            ConsentResp consentResp = aaClientSvc.consentReqAaCall(consentRequestDetail.getConsentReq());
            consentRequestDetail.setConsentResp(consentResp);
            consentRequestDetail.setConsentHandleStatus(new ConsentHandleStatus(null, "PENDING"));
            return true;
        } catch (Exception ex) {
            log.error("Error in Sending Consent Req to AA: {}", ex.getMessage());
            consentRequestDetail.setErrorInfo(new ErrorInfo("ERROR IN SENDCONSET REW", ex.getMessage()));
            return false;
        }

    }
}
