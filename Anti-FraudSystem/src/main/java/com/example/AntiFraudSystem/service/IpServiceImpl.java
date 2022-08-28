package com.example.AntiFraudSystem.service;

import com.example.AntiFraudSystem.entity.Ip;
import com.example.AntiFraudSystem.exception.BadRequestException;
import com.example.AntiFraudSystem.exception.ConflictException;
import com.example.AntiFraudSystem.exception.NotFoundException;
import com.example.AntiFraudSystem.model.StatusResponse;
import com.example.AntiFraudSystem.repository.IpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IpServiceImpl implements IpService {

    @Autowired
    IpRepository ipRepository;

    @Override
    public ResponseEntity<Ip> addSuspiciousIp(Ip ip) {

        Optional<Ip> o = ipRepository.findByIp(ip.getIp());

        if (o.isPresent()) {
            throw new ConflictException("already in database");
        }

        try {
            ipRepository.save(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(ip, HttpStatus.OK);
    }

    @Override
    public StatusResponse deleteSuspiciousIp(String ip) {

        Optional<Ip> o = ipRepository.findByIp(ip);

        //regex check valid ip format
        Pattern p = Pattern.compile(
                "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
        );
        Matcher m = p.matcher(ip);

        if (!m.matches()) {
            throw new BadRequestException("invalid ip format");
        }

        if (o.isEmpty()) {
            throw new NotFoundException("ip address not found in db");
        }

        ipRepository.delete(o.get());

        return new StatusResponse("IP " + ip + " successfully removed!");
    }

    @Override
    public List<Ip> getAllSuspiciousIp() {

        return ipRepository.findAll();
    }
}
