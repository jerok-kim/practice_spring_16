package kim.jerok.practice_spring_16.model.product;

import kim.jerok.practice_spring_16.model.MyRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ProductRepository extends MyRepository<Product> {
    
    public ProductRepository(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    protected String getEntityName() {
        return "Product";
    }
}
