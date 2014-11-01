package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 22.10.14.
 */
public class UserDaoImpl implements UserDao {

    public static final String USER_ID  = "userId";
    public static final String LOGIN    = "login";
    public static final String NAME     = "name";

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${insert_into_user_path}')).file)}")
    public String addNewUserSql;
    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${delete_user_path}')).file)}")
    public String deleteUserSql;
    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${update_user_path}')).file)}")
    public String updateUserSql;
    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_user_by_login_path}')).file)}")
    public String selectUserByLoginSql;
    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_user_by_id_path}')).file)}")
    public String selectUserByIdSql;
    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_all_users_path}')).file)}")
    public String selectAllUsersSql;

    private static final Logger LOGGER = LogManager.getLogger();

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong(USER_ID));
            user.setLogin(rs.getString(LOGIN));
            user.setName(rs.getString(NAME));
            return user;
        }
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void addUser(User user) {
        LOGGER.debug("addUser({}) ", user);
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getName(), "User name should be specified.");
        Map<String, Object> parameters = new HashMap(3);
        parameters.put(NAME, user.getName());
        parameters.put(LOGIN, user.getLogin());
        parameters.put(USER_ID, user.getUserId());
        namedJdbcTemplate.update(addNewUserSql, parameters);
    }

    @Override
    public List<User> getUsers() {
        LOGGER.debug("get users()");
        return jdbcTemplate.query(selectAllUsersSql, new UserMapper());
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("remove user(userId={}) ", userId);
        jdbcTemplate.update(deleteUserSql, userId);
    }


    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("get user ny login(login={})", login);
        return jdbcTemplate.queryForObject(selectUserByLoginSql, new String[]{login}, new UserMapper());
    }

    @Override
    public User getUserById(long userId) {
        LOGGER.debug("get user by Id(userId={})", userId);
        return jdbcTemplate.queryForObject(selectUserByIdSql, new UserMapper(), userId);
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("update user({}).. ", user);

        Map<String, Object> parameters = new HashMap(3);
        parameters.put(NAME, user.getName());
        parameters.put(LOGIN, user.getLogin());
        parameters.put(USER_ID, user.getUserId());
        namedJdbcTemplate.update(updateUserSql, parameters);
    }
}