package com.duro.edc_koko.controller.user;

import com.duro.edc_koko.entity.category.domain.Category;
import com.duro.edc_koko.entity.category.service.CategoryService;
import com.duro.edc_koko.entity.product.model.ProductDTO;
import com.duro.edc_koko.entity.product.model.ProductFilter;
import com.duro.edc_koko.entity.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/{categoryName}")
    public ModelAndView productList (@PathVariable String categoryName) {
        ModelAndView mav = new ModelAndView("user/product-categories");

        List<ProductDTO> productDTOList = productService.findByCategoryName(categoryName);
        mav.addObject("productList", productDTOList);

        getShowingProductCounts(mav, productDTOList.size());
        getCategoryList(mav);

        return mav;
    }

    @GetMapping("/filter")
    public ModelAndView categoryFilter (@ModelAttribute(value = "productFilters") ProductFilter filters) {
        ModelAndView mav = new ModelAndView("user/product-categories :: productRowFragment");

        List<ProductDTO> productDTOList = productService.findFilteredProducts(filters);

        mav.addObject("productList", productDTOList);
        getShowingProductCounts(mav, productDTOList.size());

        return mav;
    }


    /* View-relative methods */
    public void getCategoryList (ModelAndView mav) {
        Map<String, Integer> categoryListWithProductCount = new HashMap<>();
        List<Category> categoryList = categoryService.findAll();

        for (Category ctg : categoryList) {
            int categoryId = ctg.getId();
            int productWithCategoryCount = productService.countByCategory(categoryId);
            categoryListWithProductCount.put(ctg.getName(), productWithCategoryCount);
        }

        mav.addObject("categoryList", categoryListWithProductCount);
    }

    private void getShowingProductCounts (ModelAndView mav, int number) {
        int countAllProduct = productService.countAllProduct();
        String text = number + "-" + countAllProduct;
        mav.addObject("showingProductCount", text);
    }
}
