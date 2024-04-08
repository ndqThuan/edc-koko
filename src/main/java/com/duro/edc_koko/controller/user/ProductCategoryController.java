package com.duro.edc_koko.controller.user;

import com.duro.edc_koko.entity.product.model.ProductDTO;
import com.duro.edc_koko.entity.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductService productService;

    @GetMapping
    public ModelAndView categories () {
        ModelAndView mav = new ModelAndView("user/product-categories");

        List<ProductDTO> productDTOList = productService.findAll();
        mav.addObject("productList", productDTOList);

        return mav;
    }
}
