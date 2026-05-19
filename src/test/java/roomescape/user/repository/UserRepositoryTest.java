package roomescape.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.user.model.Role;
import roomescape.user.model.User;

import java.util.Optional;

@JdbcTest
class UserRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository(jdbcTemplate);

        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM schedule");
        jdbcTemplate.update("DELETE FROM theme");
        jdbcTemplate.update("DELETE FROM `user`");
    }

    @Test
    void 새로운_유저를_저장하고_생성된_고유_ID를_반환한다() {
        // given
        User user = new User("smini", "pass123", "스미니", Role.USER);

        // when
        Long id = userRepository.create(user);

        // then
        assertThat(id).isNotNull().isPositive();

        Optional<User> savedUser = userRepository.findByUserName("smini");
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getId()).isEqualTo(id);
    }

    @Test
    void 이름으로_유저를_조회하면_정확한_유저_정보를_반환한다() {
        // given
        User user = new User("smini", "pass123", "성민", Role.USER);
        Long savedId = userRepository.create(user);

        // when
        Optional<User> foundUser = userRepository.findByUserName("smini");

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(savedId);

        assertThat(foundUser.get())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(user);
    }

    @Test
    void 존재하지_않는_이름으로_유저를_조회하면_empty를_반환한다() {
        // given
        String nonExistingName = "없는유저";

        // when
        Optional<User> foundUser = userRepository.findByUserName(nonExistingName);

        // then
        assertThat(foundUser).isEmpty();
    }

    @Test
    void 중복된_이름으로_유저를_생성하면_예외가_발생한다() {
        // given
        User user1 = new User("smini", "pass123", "스미니1", Role.USER);
        userRepository.create(user1);

        // when & then
        User user2 = new User("smini", "pass456", "스미니2", Role.USER); // 동일한 username

        assertThatThrownBy(() -> userRepository.create(user2))
                .isInstanceOf(DuplicateKeyException.class);
    }
}
