package vn.iotstarts.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	 @GetMapping("/category")
	    public String categoryPage() {
	        // trả về file category.html trong thư mục resources/templates
	        return "category";
	    }

	    @GetMapping("/product")
	    public String productPage() {
	        // trả về file product.html trong thư mục resources/templates
	        return "product";
	    }
}
