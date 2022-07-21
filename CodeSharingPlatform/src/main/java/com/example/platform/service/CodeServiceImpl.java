package com.example.platform.service;

import com.example.platform.entity.Code;
import com.example.platform.exception.NotFoundException;
import com.example.platform.model.CodeDateResponse;
import com.example.platform.model.NewCodeResponse;
import com.example.platform.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    CodeRepository codeRepository;

    private final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    @Override
    public CodeDateResponse getApiCodeByUUID(String uuid) {

        Optional<Code> o;
        Code code;

        o = this.codeRepository.findByUUID(uuid);

        //check for empty
        if (o.isEmpty()) {
            throw new NotFoundException("Not Found");
        }

        code = o.get();

        //check for time, immediate throw
        if (code.getTime() > 0 && code.getTime() < LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) {
            this.codeRepository.delete(code);
            throw new NotFoundException("Not Found");
        }

        //check for view
        if (code.getViews() > 0) {
            if (code.getViews() == 1) {
                code.setViews(code.getViews() - 1);
                this.codeRepository.delete(code);
                return new CodeDateResponse(code);
            } else {
                code.setViews(code.getViews() - 1);
                this.codeRepository.save(code);
            }
        }

        return new CodeDateResponse(code);
    }

    @Override
    public ResponseEntity<String> getCodeByUUID(String uuid) {

        boolean deleteFlag = false;

        StringBuilder sb = new StringBuilder();
        Optional<Code> o = this.codeRepository.findByUUID(uuid);
        HttpHeaders responseHeaders = new HttpHeaders();
        Code code;

        if (o.isEmpty()) {
            throw new NotFoundException("Not Found");
        }
        code = o.get();
        //check for time, immediate throw
        if (code.getTime() > 0 && code.getTime() < LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) {
            this.codeRepository.delete(code);
            throw new NotFoundException("Not Found");
        }

        //check for deletion
        if (code.getViews() > 0 && code.getViews() - 1 == 0) {
            deleteFlag = true;
        }

        responseHeaders.set("Content-Type", "text/html");

        sb.append("<html>\n<head>\n");
        sb.append("<!-- Highlight.js Import -->\n");
        sb.append("<link rel=\"stylesheet\"\n");
        sb.append("         href=\"//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.6.0/build/styles/default.min.css\">\n");
        sb.append("<script src=\"//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.6.0/build/highlight.min.js\"></script>\n");
        sb.append("<script>hljs.initHighlightingOnLoad();</script>\n");
        sb.append("<title>Code</title></head>\n<body>\n");
        sb.append("<p><span id=\"load_date\"> ");
        sb.append(code.getDate().format(formatter));
        sb.append(" </span></p>\n");
        if (code.getViews() > 0) {
            code.setViews(code.getViews() - 1);
            sb.append("<p><span id=\"views_restriction\"> ");
            sb.append(code.getViews()).append(" more views allowed");
            sb.append(" </span></p>\n");
        }
        if (code.getTime() > 0) {
            sb.append("<p><span id=\"time_restriction\"> ");
            sb.append("The code will be available for ")
                    .append(code.getTime() - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                    .append(" seconds");
            sb.append(" </span></p>\n");
        }
        sb.append("<pre id=\"code_snippet\"><code>\n");
        sb.append(code.getCode());
        sb.append("</pre>\n</body>\n</html>");
        codeRepository.save(code);

        if (deleteFlag) {
            codeRepository.delete(code);
        }

        return ResponseEntity.ok().headers(responseHeaders).body(sb.toString());
    }

    @Override
    public NewCodeResponse postApiCodeNew(Code codeRequest) {

        Code code = new Code();
        code.setCode(codeRequest.getCode());
        code.setUuid(UUID.randomUUID().toString());
        code.setViews(codeRequest.getViews());
        code.setDate(LocalDateTime.now());
        code.setTime((int) (codeRequest.getTime() > 0 ? code.getDate().toEpochSecond(ZoneOffset.UTC) + codeRequest.getTime() :
                        0));

        this.codeRepository.save(code);

        return new NewCodeResponse(code.getUuid());
    }

    @Override
    public List<CodeDateResponse> getApiCodeLatest() {

        long limit;

        List<CodeDateResponse> list = new ArrayList<>();
        Optional<Code> o;
        Code code;

        limit = this.codeRepository.count() < 10 ? 1 : (this.codeRepository.count() - 9);

        for (long i = this.codeRepository.count(); i >= limit; i--) {

            o = this.codeRepository.findById(i);

            if (o.isEmpty()) {
                if (i - 1 < 1) {
                    continue;
                }
                limit--;
                continue;
            }
            code = o.get();
            if (code.getTime() > 0 || code.getViews() > 0) {
                limit--;
                continue;
            }

            list.add(new CodeDateResponse(code));

        }

        return list;
    }

    @Override
    public String getCodeLatest() {

        long limit;

        StringBuilder sb = new StringBuilder();
        Optional<Code> o;
        Code code;

        limit = this.codeRepository.count() < 10 ? 1 : (this.codeRepository.count() - 9);

        sb.append("<html>\n<head><title>Latest</title></head>\n<body>\n");

        for (long i = this.codeRepository.count(); i >= limit; i--) {

            o = this.codeRepository.findById(i);

            if (o.isEmpty()) {
                if (i - 1 < 1) {
                    continue;
                }
                limit--;
                continue;
            }
            code = o.get();
            if (code.getTime() > 0 || code.getViews() > 0) {
                limit--;
                continue;
            }

            sb.append("<span id=\"load_date\"> ")
                    .append(code.getDate().format(formatter))
                    .append(" </span>\n<pre id=\"code_snippet\">\n")
                    .append(code.getCode())
                    .append("</pre>\n");
        }

        sb.append("</body>\n</html>");

        return sb.toString();
    }
}
