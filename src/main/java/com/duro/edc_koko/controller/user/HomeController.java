package com.duro.edc_koko.controller.user;

import com.duro.edc_koko.entity.product.model.ProductDTO;
import com.duro.edc_koko.entity.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public ModelAndView userIndex () {
        ModelAndView mav = new ModelAndView("user/index");

        List<ProductDTO> productDTOs = productService.findAll();

        mav.addObject("productList", productDTOs);

        return mav;
    }


}
