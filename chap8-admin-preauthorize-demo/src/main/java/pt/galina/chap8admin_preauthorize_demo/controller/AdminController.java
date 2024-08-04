package pt.galina.chap8admin_preauthorize_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.galina.chap8admin_preauthorize_demo.service.OrderAdminService;


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
    @PostMapping("/deleteOrders")
    public String deleteAllOrders() {
        adminService.deleteAllOrders();
        return "redirect:/orders/orderList";
    }
}
