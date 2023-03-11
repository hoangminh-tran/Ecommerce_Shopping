package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/ManageCategories")
    public String ManageCategories(Model model)
    {
        List<Category> list = categoryService.findAll();
        model.addAttribute("size", list.size());
        model.addAttribute("listCategories", list);
        model.addAttribute("title", "Manage Category");
        model.addAttribute("categoryNew", new Category());
        return "ManageCategories";
    }
    @GetMapping("/search_category/{pageNo}")
    public String search_category(@PathVariable("pageNo") int pageNo,
                                  @RequestParam("name") String name,
                                  Model model)
    {
        Page<Category>page = categoryService.searchCategory(pageNo, name);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("size", page.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("title", "Manage Category");
        model.addAttribute("listCategories", page);
        return "search_category";
    }

    @GetMapping("/ManageCategories/{pageNo}")
    public String page(@PathVariable("pageNo") int pageNo, Model model)
    {
        Page<Category> page = categoryService.pageCategory(pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("size", page.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("title", "Manage Category");
        model.addAttribute("listCategories", page);
        return "ManageCategories";
    }


    @PostMapping("/add_category")
    public String add_category(@ModelAttribute("categoryNew") Category categoryNew,
                               RedirectAttributes attributes)
    {
        try
        {
            categoryService.save(categoryNew);
            attributes.addFlashAttribute("success", "Add new Category Successfully");
        }
        catch (DataIntegrityViolationException e)
        {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add because duplicated Category Name");
        }
        catch (Exception e)
        {
            attributes.addFlashAttribute("errors", "The server is error");
            e.printStackTrace();
        }
        return "redirect:/ManageCategories";
    }

    @PostMapping("/update_category")
    public String update(@RequestParam("id")Long id,
                         @RequestParam("name") String name,
                         RedirectAttributes attributes){
        try
        {
            Category category = new Category(id, name);
            categoryService.update(category);
            attributes.addFlashAttribute("success","Updated successfully");
        }
        catch (DataIntegrityViolationException e)
        {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/ManageCategories";
    }

    @GetMapping(value = "/delete_category/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to deleted");
        }
        return "redirect:/ManageCategories";
    }

    @GetMapping(value = "/enable_category/{id}")
    public String enable(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            categoryService.enableById(id);
            attributes.addFlashAttribute("success", "Enabled successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enabled");
        }
        return "redirect:/ManageCategories";
    }

}
