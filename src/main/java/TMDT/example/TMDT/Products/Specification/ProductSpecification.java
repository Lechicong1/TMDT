package TMDT.example.TMDT.Products.Specification;

import TMDT.example.TMDT.Products.Entity.ProductEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<ProductEntity> search(String shopAddress,Integer categoryId ,String nameProduct) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // fullName LIKE CONCAT(name, '%')
            if (shopAddress != null && !shopAddress.isEmpty()) {
                predicates.add(cb.equal(root.get("shop").get("shopAddress"), shopAddress ));
            }
            if (nameProduct != null && !nameProduct.isEmpty()) {
                predicates.add(cb.like(root.get("name"),"%"+ nameProduct +"%" ));
            }
            if(categoryId != null){
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
