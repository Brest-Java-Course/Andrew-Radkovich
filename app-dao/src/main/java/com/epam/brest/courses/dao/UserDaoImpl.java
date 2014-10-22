package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by andrew on 20.10.14.
 */
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
        //return null;
        return jdbcTemplate.query("select userId, login, name from USER", new UserMapper());
    }

    @Override
    public void removeUser(long UserId) {

    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update("INSERT INTO USER  (userId, login, name) VALUES(?,?,?)", user.getUserId(), user.getLogin(), user.getUserName());
    }
}
