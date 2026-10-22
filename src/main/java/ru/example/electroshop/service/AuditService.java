package ru.example.electroshop.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.slf4j.*;
import ru.example.electroshop.entity.UserAction;
import ru.example.electroshop.repository.UserActionRepository;

@Service
public class AuditService {
    private static final Logger log=LoggerFactory.getLogger(AuditService.class);
    private final UserActionRepository repo;
    public AuditService(UserActionRepository r) {
        repo=r;
    }
    public void record(Authentication a,String d) {
        String u=a==null?"anonymous":a.getName();
        repo.save(new UserAction(u,d));
        log.info("user={} action={}",u,d);
    }
}
