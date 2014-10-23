package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong("userId"));
            user.setLogin(rs.getString("login"));
            user.setUserName(rs.getString("name"));
            return user;
        }
    }

    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) dataSource);
    }

    @Override
    public List<User> getUsers(){
        return jdbcTemplate.query("SELECT userId, login, name FROM USER", new UserMapper());
    }

    @Override
    public void removeUser(long userId) {
        jdbcTemplate.update("DELETE FROM USER WHERE userId=?", userId);
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update("INSERT INTO USER  (userId, login, name) VALUES(?,?,?)", user.getUserId(), user.getLogin(), user.getUserName());
    }

    @Override
    public User getUserById(long userId){
        return jdbcTemplate.queryForObject("SELECT userId, login, name FROM USER WHERE userId=?", new Object[]{userId}, new UserMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        return jdbcTemplate.queryForObject("SELECT userId, login, name FROM USER WHERE login=?", new Object[]{login}, new UserMapper());
    }
}
