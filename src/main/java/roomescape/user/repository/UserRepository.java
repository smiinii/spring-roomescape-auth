package roomescape.user.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.user.model.Role;
import roomescape.user.model.User;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(User user) {
        String sql = "INSERT INTO  `user` (username, password, nickname, role) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getPassword());
                    ps.setString(3, user.getNickname());
                    ps.setString(4, user.getRole().name());
                    return ps;
                }, keyHolder);
        return  keyHolder.getKey().longValue();
    }

    public Optional<User> findByUserName(String name) {
        String sql = "SELECT id, username, password, nickname, role, store_id, token_version FROM `user` WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    Role.valueOf(rs.getString("role")),
                    rs.getObject("store_id", Long.class),
                    rs.getInt("token_version")
            ), name);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void incrementTokenVersion(Long userId) {
        String sql = "UPDATE `user` SET token_version = token_version + 1 WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
