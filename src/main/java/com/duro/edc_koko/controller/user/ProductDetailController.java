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
        ModelAndView mav = new ModelAndView("user/product-detail");

        ProductDTO thisProduct = productService.get(id);
        mav.addObject("thisProduct", thisProduct);
        mav.addObject("productDetailName", thisProduct.getName());

        List<ProductDTO> productDTOs = productService.findByCategoryName(thisProduct.getCategory());
        mav.addObject("productList", productDTOs);

        return mav;
    }
}
