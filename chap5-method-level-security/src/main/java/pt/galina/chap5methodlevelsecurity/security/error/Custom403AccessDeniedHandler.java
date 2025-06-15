package pt.galina.chap5methodlevelsecurity.security.error;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Custom403AccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        //  Getting the real message from Spring Security
        String message = accessDeniedException.getMessage();


        request.setAttribute("javax.servlet.error.status_code", 403);
        request.setAttribute("javax.servlet.error.message", message);
        request.setAttribute("jakarta.servlet.error.message", message);

        request.getRequestDispatcher("/error").forward(request, response);
    }
}
