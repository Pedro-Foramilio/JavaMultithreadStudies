package com.virtual.thread.sec09.controller;

import com.virtual.thread.sec09.security.SecurityContext;
import com.virtual.thread.sec09.security.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class DocController {
    private static final Logger log = LoggerFactory.getLogger(DocController.class);
    private final Supplier<SecurityContext> securityContextSupplier;


    public DocController(Supplier<SecurityContext> securityContextSupplier) {
        this.securityContextSupplier = securityContextSupplier;
    }

    public void read() {
        this.validateUserRole(UserRole.VIEWER);
        log.info("viewing document...");
    }

    public void edit() {
        this.validateUserRole(UserRole.EDITOR);
        log.info("editing document...");
    }

    public void delete() {
        this.validateUserRole(UserRole.ADMIN);
        log.info("deleting document...");
    }

    private void validateUserRole(UserRole requiredRole) {
        SecurityContext securityContext = securityContextSupplier.get();

        if (securityContext.hasPermission(requiredRole)) {
            log.error("User with id {} and role {} is not authorized to perform this action.",
                    securityContext.userId(), securityContext.role());
        }
    }

}
