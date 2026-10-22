package ru.example.electroshop.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import ru.example.electroshop.entity.*;
import ru.example.electroshop.repository.UserRepository;

final class AccessHelper {
    private AccessHelper() {
    }
    static boolean has(Authentication a,String role) {
        return a!=null&&a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_"+role));
    }
    static boolean admin(Authentication a) {
        return has(a,"ADMIN");
    }
    static boolean readOnly(Authentication a) {
        return has(a,"READ_ONLY");
    }
    static void write(Authentication a) {
        if(readOnly(a))throw new AccessDeniedException("Роль READ_ONLY допускает только просмотр");
    }
    static AppUser current(Authentication a,UserRepository users) {
        return users.findByUsername(a.getName()).orElseThrow();
    }
    static void own(AppUser owner,Authentication a) {
        if(!admin(a)&&(owner==null||!owner.getUsername().equals(a.getName())))throw new AccessDeniedException("Нет доступа к чужой записи");
    }
}
