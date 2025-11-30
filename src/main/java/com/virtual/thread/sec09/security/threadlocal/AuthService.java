package com.virtual.thread.sec09.security.threadlocal;

import com.virtual.thread.sec09.security.SecurityContext;
import com.virtual.thread.sec09.security.UserRole;

import java.util.Map;

public class AuthService {

    public static final String VALID_PASSWORD = "password";
    public static final Map<Integer, UserRole> USER_ROLES = Map.of(
            1, UserRole.ADMIN,
            2, UserRole.EDITOR,
            3, UserRole.VIEWER
    );

    public static void loginAndExecute(Integer userId, String password, Runnable action) {

        if (!VALID_PASSWORD.equals(password)) {
            throw new SecurityException("Invalid password for user id: " + userId);
        }

        try {
            var securityContext = new SecurityContext(userId, USER_ROLES.getOrDefault(userId, UserRole.ANONYMOUS));
            SecurityContextHolder.setContext(securityContext);
            action.run();
        } finally {
            SecurityContextHolder.clear();
        }

    }

}
