package TMDT.example.TMDT.Products.Repo;

import TMDT.example.TMDT.Products.Entity.ProductEntity;
import TMDT.example.TMDT.Products.Payload.Reponse.ProductDetailDTO;
import TMDT.example.TMDT.Products.Payload.projection.ProductDetailView;
import TMDT.example.TMDT.Respone.PageReponse;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long>, JpaSpecificationExecutor<ProductEntity> {
    @Query(value = "select  p from ProductEntity p where p.shop = :shop ")
    List<ProductEntity> findAllProductByShop(ShopEntity shop);
    @EntityGraph(attributePaths = {"shop", "category"})
    Page<ProductEntity> findAll(Specification<ProductEntity> spec , Pageable pageable);
    @Query(value = "   select p.id as idProduct ,\n" +
            "            p.name as nameProduct ,\n" +
            "            p.productImage as productImage,\n" +
            "            p.description , \n" +
            "            c.name as nameCategory ,\n" +
            "            pr.id as variantId,\n" +
            "              pr.price as priceVariant,\n" +
            "            pr.quantity as variantQuantity,\n" +
            "            atrr.attributeType as attributeType  ,\n" +
            "            v.value ,\n" +
            "            v.imageVariant as imageVariant\n" +
            "    from product p \n" +
            "    left join products_variant pr on pr.product_id= p.id\n" +
            "    left join variant_attributes_values v on pr.id = v.variant_id\n" +
            "    left join variant_attributes atrr on atrr.id=v.attribute_id\n" +
            "    inner join category c on c.id =p.category_id\n" +
            "    where p.id = :id",nativeQuery = true)
    List<ProductDetailView> getProductDetail(Long id);
}
