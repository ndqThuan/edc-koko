package com.duro.edc_koko.controller.user;

import com.duro.edc_koko.entity.category.model.CategoryDTO;
import com.duro.edc_koko.entity.category.service.CategoryService;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/layout")
@RequiredArgsConstructor
public class LayoutController {

    private final CategoryService categoryService;

    @HxRequest
    @GetMapping("/navbar-categories")
    public ModelAndView getCategoriesForNavbar () {
        ModelAndView mav = new ModelAndView("user/layout :: navbarCategories");

        List<CategoryDTO> categoryDTOList = categoryService.find5Categories();
        mav.addObject("navbarCategories", categoryDTOList);

        return mav;
    }

    @HxRequest
    @GetMapping("/searchbar-categories")
    public ModelAndView getCategoriesForSearchbar () {
        ModelAndView mav = new ModelAndView("user/layout :: searchbarCategories");

        List<CategoryDTO> categoryDTOList = categoryService.findAll();
        mav.addObject("searchbarCategories", categoryDTOList);

        return mav;
    }
}
