package kim.jerok.practice_spring_16.model.user;

import kim.jerok.practice_spring_16.model.MyDummyEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest extends MyDummyEntity {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    @DisplayName("save() test")
    public void save_test() throws Exception {
        // given
        User user = newUser("jerok");

        // when
        User userPS = userRepository.save(user);

        // then
        assertThat(userPS.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findById() test")
    public void findById_test() throws Exception {
        // given 1 - DB에 영속화
        userRepository.save(newUser("jerok"));
        em.clear();  // 준영속

        // given 2
        int id = 1;

        // when
        User userPS = userRepository.findById(id);  // 캐싱이 안될듯 - 조회 쿼리가 일어난다
        // userPS => 영속 객체

        // then
        assertThat(userPS.getUsername()).isEqualTo("jerok");
    }

    @Test
    @DisplayName("update() test")
    public void update_test() throws Exception {
        // given 1 - DB에 영속화
        userRepository.save(newUser("jerok"));
        em.clear();

        // given 2 - request 데이터
        String password = "5678";
        String email = "jerok.kim@gmail.com";

        // when
        User userPS = userRepository.findById(1);  // 쿼리 날아감 -> 영속화
        userPS.update(password, email);  // 데이터 수정 (hibernate만 변경감지를 해준다)

        User updateUserPS = userRepository.save(userPS);  // merge (PK가 있으니까)
        em.flush();  // rollback 되니까 테스트시에만 붙여주자.

        // then
        assertThat(updateUserPS.getPassword()).isEqualTo("5678");
    }  // rollback

    @Test
    @DisplayName("update_dirty_checking() test")
    public void update_dirty_checking_test() throws Exception {
        // given 1 - DB에 영속화
        userRepository.save(newUser("jerok"));
        em.clear();

        // given 2 - request 데이터
        String password = "5678";
        String email = "jerok.kim@gmail.com";

        // when
        User userPS = userRepository.findById(1);
        userPS.update(password, email);
        em.flush();

        // then
        User updateUserPS = userRepository.findById(1);
        assertThat(updateUserPS.getPassword()).isEqualTo("5678");
    }

    @Test
    @DisplayName("delete() test")
    public void delete_test() throws Exception {
        // given 1 - DB에 영속화
        userRepository.save(newUser("jerok"));
        em.clear();  // 준영속

        // given 2 - request 데이터
        int id = 1;
        User findUserPS = userRepository.findById(id);

        // DB는 read할 때보다, 상대적으로 write할 때, 다른 사람들이 멍때리게 됨.
        // when
        userRepository.delete(findUserPS);

        // then
        User deleteUserPS = userRepository.findById(1);
        assertThat(deleteUserPS).isNull();
    }

    @Test
    @DisplayName("findAll() test")
    public void findAll_test() throws Exception {
        // given
        List<User> userList = Arrays.asList(newUser("jerok"), newUser("chowon"));
        userList.stream().forEach((user) -> {
            userRepository.save(user);
        });
        em.clear();

        // when
        List<User> userListPS = userRepository.findAll();
        System.out.println("테스트 : " + userListPS);

        // then
        assertThat(userListPS.size()).isEqualTo(2);
    }
}
