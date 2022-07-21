package com.example.platform.service;

import com.example.platform.entity.Code;
import com.example.platform.model.CodeDateResponse;
import com.example.platform.model.NewCodeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CodeService {

    CodeDateResponse getApiCodeByUUID(String uuid);

    ResponseEntity<String> getCodeByUUID(String uuid);

    NewCodeResponse postApiCodeNew(Code code);

    List<CodeDateResponse> getApiCodeLatest();

    String getCodeLatest();
}
