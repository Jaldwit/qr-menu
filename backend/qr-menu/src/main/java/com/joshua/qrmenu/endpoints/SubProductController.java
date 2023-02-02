package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.ProductAssembler;
import com.joshua.qrmenu.endpoints.assemblers.SubProductAssembler;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.BaseController;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.services.SubProductService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubProductController extends BaseController {

        private final SubProductService subProductService;
        private final SubProductAssembler subProductAssembler;

        public SubProductController(SubProductService subProductService, SubProductAssembler subProductAssembler) {
            this.subProductService = subProductService;
            this.subProductAssembler = subProductAssembler;
        }

        @GetMapping("/categories/{categoryId}/subcategories/{subcategoryId}/products")
        public CollectionModel<EntityModel<Product>> getSubcategoryProducts(@PathVariable Long categoryId, @PathVariable Long subcategoryId) throws NotFoundException {
            return subProductAssembler.toCollectionModel(subProductService.getSubcategoryProducts(categoryId, subcategoryId), categoryId, subcategoryId);
        }
}
