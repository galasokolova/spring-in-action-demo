package pt.galina.chap5methodlevelsecurity.security.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute("javax.servlet.error.status_code");
        Object error = request.getAttribute("javax.servlet.error.message");
        Object message = request.getAttribute("jakarta.servlet.error.message");

        model.addAttribute("status", statusCode != null ? statusCode : 999);
        model.addAttribute("error", error != null ? error : "Unknown Error");
        model.addAttribute("message", message != null ? message : "No message available");

        return "error";
    }
}
