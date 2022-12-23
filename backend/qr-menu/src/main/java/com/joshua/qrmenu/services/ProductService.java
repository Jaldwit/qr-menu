package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.ShallowCopy;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.services.util.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to handle domain logic about Products.
 */
@Service
public class ProductService extends AbstractService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    /**
     * Constructor for a ProductService
     *
     * @param productRepository : a ProductRepository.
     * @param productMapper : a ProductMapper to create EntityModels with HATEAOS links.
     */
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Returns a list of products.
     *
     * @return : A list of all products.
     */
    public List<Product> getAll() {
        Stream<ProductEntity> productEntityStream = productRepository.findAll().stream();

        return productEntityStream
                .map(productMapper::entityToJson)
                .collect(Collectors.toList());

    }

    /**
     * Return a product with a specific ID if found.
     *
     * @param id : ID of the product to be retrieved.
     * @return : The product with the given ID.
     * @throws NotFoundException : When the ID doesn't correspond to an existing product.
     */
    public Product getProductById(Long id) throws NotFoundException {
        ProductEntity productEntity = parseOptional(productRepository.findById(id));
        return productMapper.entityToJson(productEntity);
    }

    /**
     * Create a new product.
     *
     * @param newProduct : An object with all data to create the new product.
     * @return : A Product representing the newly created product.
     */
    public Product createNewProduct(NewProduct newProduct) {
        ProductEntity productEntity = productMapper.newJsonToEntity(newProduct);
        productEntity = productRepository.save(productEntity);
        return productMapper.entityToJson(productEntity);
    }

    /**
     * Delete a product with a specific ID if found.
     *
     * @param id : ID of the product to be deleted.
     * @throws NotFoundException : When the ID doesn't correspond to an existing product.
     */
    public void deleteProductById(Long id) throws NotFoundException {
        productRepository.deleteById(id);
    }

    /**
     * Update data of an existing product with a specific ID if found.
     *
     * @param id : The ID of the product to be updated.
     * @param newProduct : An Object containing data to be changed.
     * @return : The Product with the updated fields.
     * @throws NotFoundException : When the ID doesn't correspond to an existing product.
     */
    public Product patchProductById(Long id, NewProduct newProduct) throws NotFoundException {
        ProductEntity originalEntity = parseOptional(productRepository.findById(id));
        ProductEntity newEntity = productMapper.newJsonToEntity(newProduct);

        ShallowCopy.copyFieldsExceptNull(newEntity, originalEntity);
        productRepository.save(originalEntity);
        return productMapper.entityToJson(originalEntity);

    }
}
