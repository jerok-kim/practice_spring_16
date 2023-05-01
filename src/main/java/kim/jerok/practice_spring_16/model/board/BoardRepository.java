package kim.jerok.practice_spring_16.model.board;

import kim.jerok.practice_spring_16.model.MyRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BoardRepository extends MyRepository<Board> {

    public BoardRepository(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<Board> getEntityClass() {
        return Board.class;
    }

    @Override
    protected String getEntityName() {
        return "Board";
    }
}
