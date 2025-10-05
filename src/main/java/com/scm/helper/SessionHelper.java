package com.scm.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
    // Implementation to remove session attributes
     public static void removeSessionAttributes() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attr != null) {
                HttpSession session = attr.getRequest().getSession();
                if (session != null) {
                    session.removeAttribute("successMessage");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in removing session attributes");
            e.printStackTrace();
        }
    }
    
}
