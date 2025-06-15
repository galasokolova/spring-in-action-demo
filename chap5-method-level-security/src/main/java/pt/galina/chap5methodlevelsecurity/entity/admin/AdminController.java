package pt.galina.chap5methodlevelsecurity.entity.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final OrderAdminService adminService;
    public AdminController(OrderAdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String adminPanel() {
        return "admin";
    }

    //access to this method is restricted by @PreAuthorized in OrderAdminService
    @PostMapping("/deleteOrders")
    public String deleteAllOrders(RedirectAttributes redirectAttributes) {

        adminService.deleteAllOrders();
        redirectAttributes.addFlashAttribute("message", "All orders have been deleted!");
        return "redirect:/admin";
    }

}
