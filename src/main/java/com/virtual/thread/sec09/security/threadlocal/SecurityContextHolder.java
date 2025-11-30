package com.virtual.thread.sec09.security.threadlocal;

import com.virtual.thread.sec09.security.SecurityContext;
import com.virtual.thread.sec09.security.UserRole;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.ANONYMOUS);
    private static final ThreadLocal<SecurityContext> contextHolder = ThreadLocal.withInitial(() -> ANONYMOUS_CONTEXT);

    static void setContext(SecurityContext context) {
        contextHolder.set(context);
    }

    static void clear() {
        contextHolder.remove();
    }

    public static SecurityContext getContext() {
        return contextHolder.get();
    }

}
