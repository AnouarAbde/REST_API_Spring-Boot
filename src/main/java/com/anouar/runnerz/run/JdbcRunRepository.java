package com.anouar.runnerz.run;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRunRepository implements RunRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRunRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Run> findAll() {
        String sql = "SELECT * FROM run";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Run.class));
    }

    public Optional<Run> findById(Integer id) {
        String sql = "SELECT id, title, started_on, completed_on, miles, location FROM run WHERE id = ?";
        List<Run> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Run.class), id);
        return results.stream().findFirst();
    }

    public void create(Run run) {
        String sql = "INSERT INTO run(id, title, started_on, completed_on, miles, location) VALUES (?, ?, ?, ?, ?, ?)";
        int updated = jdbcTemplate.update(sql,
                run.id(),
                run.title(),
                run.startedOn(),
                run.completedOn(),
                run.miles(),
                run.location().toString());

        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    public void update(Run run, Integer id) {
        String sql = "UPDATE run SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql,
                run.title(),
                run.startedOn(),
                run.completedOn(),
                run.miles(),
                run.location().toString(),
                id);

        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM run WHERE id = ?";
        int updated = jdbcTemplate.update(sql, id);

        Assert.state(updated == 1, "Failed to delete run " + id);
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM run";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        String sql = "SELECT * FROM run WHERE location = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Run.class), location);
    }
}
