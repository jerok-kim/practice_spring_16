package kim.jerok.practice_spring_16.model.user;

import kim.jerok.practice_spring_16.model.MyRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepository extends MyRepository<User> {

    public UserRepository(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected String getEntityName() {
        return "User";
    }


}
