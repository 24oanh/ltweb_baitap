package vn.iotstar.controller;

import vn.iotstar.entity.Video;
import vn.iotstar.service.VideoService;
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
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listVideos(Model model) {
        model.addAttribute("videos", videoService.findAll());
        return "video/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("video", new Video());
        model.addAttribute("categories", categoryService.findAll());
        return "video/form";
    }

    @PostMapping("/add")
    public String addVideo(@Valid @ModelAttribute Video video,
                         BindingResult result,
                         @RequestParam("imageUpload") MultipartFile file,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "video/form";
        }

        if (!file.isEmpty()) {
            video.setImageFile(file.getOriginalFilename());
        }

        videoService.save(video);
        redirectAttributes.addFlashAttribute("successMsg", "Thêm video thành công!");
        return "redirect:/videos";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Video video = videoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy video"));
        model.addAttribute("video", video);
        model.addAttribute("categories", categoryService.findAll());
        return "video/form";
    }

    @PostMapping("/edit/{id}")
    public String updateVideo(@PathVariable Long id,
                            @Valid @ModelAttribute Video video,
                            BindingResult result,
                            @RequestParam("imageUpload") MultipartFile file,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "video/form";
        }

        Video existingVideo = videoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy video"));

        video.setId(id);
        video.setViews(existingVideo.getViews());
        
        if (file.isEmpty()) {
            video.setImageFile(existingVideo.getImageFile());
        } else {
            video.setImageFile(file.getOriginalFilename());
        }

        videoService.save(video);
        redirectAttributes.addFlashAttribute("successMsg", "Cập nhật video thành công!");
        return "redirect:/videos";
    }

    @PostMapping("/delete/{id}")
    public String deleteVideo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        videoService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMsg", "Xóa video thành công!");
        return "redirect:/videos";
    }
}