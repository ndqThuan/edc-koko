package com.duro.edc_koko.controller.user;

import com.duro.edc_koko.entity.product.model.ProductDTO;
import com.duro.edc_koko.entity.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ModelAndView productDetail (@PathVariable(value = "id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("user/product-detail");

        ProductDTO thisProduct = productService.get(id);
        modelAndView.addObject("thisProduct", thisProduct);

        List<ProductDTO> productDTOs = productService.getProductsFromCategory(thisProduct.getCategory());
        modelAndView.addObject("productList", productDTOs);

        return modelAndView;
    }
}
