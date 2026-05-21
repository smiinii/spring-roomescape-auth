package roomescape.store.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.store.model.Store;

import java.util.List;

@Repository
public class StoreRepository {

    private final JdbcTemplate jdbcTemplate;

    public StoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Store> findAll() {
        String sql = "SELECT id, store_number, name FROM store";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Store(
                rs.getLong("id"),
                rs.getString("store_number"),
                rs.getString("name")
        ));
    }
}
