package com.duro.edc_koko.controller.user;

import com.duro.edc_koko.entity.category.service.CategoryService;
import com.duro.edc_koko.entity.product.model.ProductDTO;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/layout")
@RequiredArgsConstructor
public class LayoutController {

    private final CategoryService categoryService;

    @HxRequest
    @GetMapping("/navbar-categories")
    public ModelAndView getShoppingCart () {
        ModelAndView mav = new ModelAndView("user/layout :: shoppingCart");

        Map<ProductDTO, Integer> cartMap = new HashMap<>();

        return mav;
    }

}
