package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    public static final String USER_ID = "userId";
    public static final String USER_LOGIN = "login";
    public static final String USER_NAME = "name";

    public static final String SELECT_ALL_USERS_SQL = "SELECT userId, login, name FROM USER";
    public static final String DELETE_USER_SQL = "DELETE FROM USER WHERE userId=?";
    public static final String INSERT_USER_SQL = "INSERT INTO USER  (userId, login, name) VALUES(?,?,?)";
    public static final String SELECT_USER_BY_ID_SQL = "SELECT userId, login, name FROM USER WHERE userId=?";
    public static final String SELECT_USER_BY_LOGIN_SQL = "SELECT userId, login, name FROM USER WHERE login=?";
    public static final String UPDATE_USER_SQL = "update USER set name = :name, login = :login where userId = :userId";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public class UserMapper implements RowMapper<User> {



        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong(USER_ID));
            user.setLogin(rs.getString(USER_LOGIN));
            user.setUserName(rs.getString(USER_NAME));
            return user;
        }
    }

    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getUsers(){
        return jdbcTemplate.query(SELECT_ALL_USERS_SQL, new UserMapper());
    }

    @Override
    public void removeUser(Long userId) {
        jdbcTemplate.update(DELETE_USER_SQL, userId);
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update(INSERT_USER_SQL, user.getUserId(), user.getLogin(), user.getUserName());
    }

    @Override
    public User getUserById(Long userId){
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID_SQL, new Object[]{userId}, new UserMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_LOGIN_SQL, new Object[]{login}, new UserMapper());
    }

    @Override
    public void updateUser(User user) {
        Map<String, Object> parameters = new HashMap(3);
        parameters.put(USER_NAME, user.getUserName());
        parameters.put(USER_LOGIN, user.getLogin());
        parameters.put(USER_ID, user.getUserId());
        namedParameterJdbcTemplate.update(UPDATE_USER_SQL, parameters);
    }
}
