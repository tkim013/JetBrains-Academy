package com.example.platform.controller;

import com.example.platform.entity.Code;
import com.example.platform.model.CodeDateResponse;
import com.example.platform.model.NewCodeResponse;
import com.example.platform.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    CodeService codeService;

    @GetMapping("api/code/{uuid}")
    CodeDateResponse getApiCodeById(@PathVariable String uuid) {

        return this.codeService.getApiCodeByUUID(uuid);
    }

    @GetMapping(value = "code/{uuid}", produces = MediaType.TEXT_HTML_VALUE)
    ResponseEntity<String> getCodeById(@PathVariable String uuid) {

        return this.codeService.getCodeByUUID(uuid);
    }

    @PostMapping("/api/code/new")
    NewCodeResponse postApiCodeNew(@RequestBody Code code) {

        return this.codeService.postApiCodeNew(code);
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    String getCodeNew() {

        return "<textarea id=\"code_snippet\"> ... </textarea>\n" +
                "<title>Create</title>\n" +
                "<p><input id=\"time_restriction\" type=\"text\"/>\n</p>" +
                "<p><input id=\"views_restriction\" type=\"text\"/>\n</p>" +
                "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>";
    }

    @GetMapping("/api/code/latest")
    List<CodeDateResponse> getApiCodeLatest() {

        return codeService.getApiCodeLatest();
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    String getCodeLatest() {

        return codeService.getCodeLatest();
    }
}
