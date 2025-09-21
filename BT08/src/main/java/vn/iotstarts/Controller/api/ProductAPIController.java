package vn.iotstarts.Controller.api;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstarts.entity.Product;
import vn.iotstarts.entity.Category;
import vn.iotstarts.model.Response;
import vn.iotstarts.service.IProductService;
import vn.iotstarts.service.ICategoryService;
import vn.iotstarts.service.IStorageService;

@RestController
@RequestMapping(path = "/api/product")
public class ProductAPIController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    // Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<Response>(new Response(true, "Thành công", productService.findAll()), HttpStatus.OK);
    }

    // Lấy sản phẩm theo id
    @PostMapping(path = "/getProduct")
    public ResponseEntity<?> getProduct(@Validated @RequestParam("id") Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<Response>(new Response(true, "Thành công", product.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Product", null),
                    HttpStatus.NOT_FOUND);
        }
    }

    // Thêm sản phẩm
    @PostMapping(path = "/addProduct")
    public ResponseEntity<?> addProduct(
            @Validated @RequestParam("productName") String productName,
            @Validated @RequestParam("price") Double price,
            @Validated @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);

        // set category
        Optional<Category> category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Category", null),
                    HttpStatus.BAD_REQUEST);
        }
        product.setCategory(category.get());

        // upload image nếu có
        if (image != null && !image.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            product.setImage(storageService.getSorageFilename(image, uuString));
            storageService.store(image, product.getImage());
        }

        productService.save(product);
        return new ResponseEntity<Response>(new Response(true, "Thêm Thành công", product), HttpStatus.OK);
    }

    // Cập nhật sản phẩm
    @PutMapping(path = "/updateProduct")
    public ResponseEntity<?> updateProduct(
            @Validated @RequestParam("productId") Long productId,
            @Validated @RequestParam("productName") String productName,
            @Validated @RequestParam("price") Double price,
            @Validated @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Product", null),
                    HttpStatus.BAD_REQUEST);
        }

        Product product = optProduct.get();
        product.setProductName(productName);
        product.setPrice(price);

        // update category
        Optional<Category> category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Category", null),
                    HttpStatus.BAD_REQUEST);
        }
        product.setCategory(category.get());

        // upload image nếu có
        if (image != null && !image.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            product.setImage(storageService.getSorageFilename(image, uuString));
            storageService.store(image, product.getImage());
        }

        productService.save(product);
        return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", product), HttpStatus.OK);
    }

    // Xóa sản phẩm
    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity<?> deleteProduct(@Validated @RequestParam("productId") Long productId) {
        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Product", null),
                    HttpStatus.BAD_REQUEST);
        } else {
            productService.delete(optProduct.get());
            return new ResponseEntity<Response>(new Response(true, "Xóa Thành công", optProduct.get()), HttpStatus.OK);
        }
    }
}
