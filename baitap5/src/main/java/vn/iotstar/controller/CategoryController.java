package vn.iotstar.controller;

import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/form";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute Category category,
                            BindingResult result,
                            @RequestParam("imageFile") MultipartFile file,
                            RedirectAttributes redirectAttributes) {
        
        if (categoryService.existsByCode(category.getCode())) {
            result.rejectValue("code", "error.category", "Mã code đã tồn tại!");
        }

        if (result.hasErrors()) {
            return "category/form";
        }

        // Handle file upload (simplified)
        if (!file.isEmpty()) {
            category.setImage(file.getOriginalFilename());
        }

        categoryService.save(category);
        redirectAttributes.addFlashAttribute("successMsg", "Thêm danh mục thành công!");
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
        model.addAttribute("category", category);
        return "category/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id,
                               @Valid @ModelAttribute Category category,
                               BindingResult result,
                               @RequestParam("imageFile") MultipartFile file,
                               RedirectAttributes redirectAttributes) {
        
        Category existingCategory = categoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));

        if (!existingCategory.getCode().equals(category.getCode()) && 
            categoryService.existsByCode(category.getCode())) {
            result.rejectValue("code", "error.category", "Mã code đã tồn tại!");
        }

        if (result.hasErrors()) {
            return "category/form";
        }

        category.setId(id);
        if (file.isEmpty()) {
            category.setImage(existingCategory.getImage());
        } else {
            category.setImage(file.getOriginalFilename());
        }

        categoryService.save(category);
        redirectAttributes.addFlashAttribute("successMsg", "Cập nhật danh mục thành công!");
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMsg", "Xóa danh mục thành công!");
        return "redirect:/categories";
    }
}