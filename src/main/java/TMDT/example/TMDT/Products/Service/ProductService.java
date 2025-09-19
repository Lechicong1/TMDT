package TMDT.example.TMDT.Products.Service;

import TMDT.example.TMDT.Products.Payload.Reponse.ProductDetailDTO;
import TMDT.example.TMDT.Products.Payload.Reponse.ProductReponse;
import TMDT.example.TMDT.Products.Payload.Request.ProductRequest;
import TMDT.example.TMDT.Respone.PageReponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public Long CreateProduct(ProductRequest productRequest, MultipartFile productImage);
    public Long UpdateProduct(Long id , ProductRequest productRequest,MultipartFile productImage);
    public void DeleteProduct(Long id);
    PageReponse<ProductReponse> getAllProducts(String shopAddress,
                                               Integer categoryId,
                                               String nameProduct,
                                               Integer page,Integer size,String sortBy);
    List<ProductReponse> getAllProductsByShop();
    List<ProductDetailDTO> getDetailProduct(Long id);
}
