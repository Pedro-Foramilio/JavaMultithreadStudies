package com.virtual.thread.sec09;

import com.virtual.thread.sec09.controller.DocController;
import com.virtual.thread.sec09.security.threadlocal.AuthService;
import com.virtual.thread.sec09.security.threadlocal.SecurityContextHolder;
import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class AccessWithThreadLocal {
    private static final Logger log = LoggerFactory.getLogger(AccessWithThreadLocal.class);

    public static final DocController docController = new DocController(
            SecurityContextHolder::getContext
    );

    static void main(String[] args) {
        Thread.ofVirtual().start(() -> documentAccesWorkflow(1, "password"));
        Thread.ofVirtual().start(() -> documentAccesWorkflow(2, "password"));
        Thread.ofVirtual().start(() -> documentAccesWorkflow(3, "password"));

        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    static void documentAccesWorkflow(Integer userid, String password) {
        AuthService.loginAndExecute(userid, password, () -> {
            docController.read();
            docController.edit();
            docController.delete();
        });
    }

}
