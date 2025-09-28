package vn.iotstar.controller;

import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "user/form";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute User user,
                        BindingResult result,
                        @RequestParam("avatarFile") MultipartFile file,
                        RedirectAttributes redirectAttributes) {

        if (userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username đã tồn tại!");
        }

        if (userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email đã tồn tại!");
        }

        if (result.hasErrors()) {
            return "user/form";
        }

        if (!file.isEmpty()) {
            user.setAvatar(file.getOriginalFilename());
        }

        userService.save(user);
        redirectAttributes.addFlashAttribute("successMsg", "Thêm người dùng thành công!");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        model.addAttribute("user", user);
        return "user/form";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                           @Valid @ModelAttribute User user,
                           BindingResult result,
                           @RequestParam("avatarFile") MultipartFile file,
                           RedirectAttributes redirectAttributes) {

        User existingUser = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        if (!existingUser.getUsername().equals(user.getUsername()) && 
            userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username đã tồn tại!");
        }

        if (!existingUser.getEmail().equals(user.getEmail()) && 
            userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email đã tồn tại!");
        }

        if (result.hasErrors()) {
            return "user/form";
        }

        user.setId(id);
        user.setPassword(existingUser.getPassword());
        
        if (file.isEmpty()) {
            user.setAvatar(existingUser.getAvatar());
        } else {
            user.setAvatar(file.getOriginalFilename());
        }

        userService.save(user);
        redirectAttributes.addFlashAttribute("successMsg", "Cập nhật người dùng thành công!");
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMsg", "Xóa người dùng thành công!");
        return "redirect:/users";
    }
}