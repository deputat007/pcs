package restservice.repositories;

import com.zeroisbiggerthanone.pcs.entities.User;
import com.zeroisbiggerthanone.pcs.entities.UserBase;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    String SELECT_BY_ID = "SELECT tbl_user.id, user_base_id, password_id FROM tbl_user " +
            "WHERE id = #{id} AND tbl_user.deleted IS NULL;";
    String SELECT_BY_LOGIN = "SELECT tbl_user.id, user_base_id, password_id FROM tbl_user INNER JOIN tbl_user_base " +
            "ON user_base_id = tbl_user_base.id WHERE tbl_user_base.login = #{login} AND tbl_user.deleted IS NULL;";
    String INSERT = "INSERT INTO tbl_user (id, user_base_id, password_id) VALUES (#{id}, #{userBase.id}, #{password.id});";

    @Select(SELECT_BY_LOGIN)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "user_base_id", property = "userBase", javaType = UserBase.class,
                    one = @One(select = "restservice.repositories.UserBaseDao.getById")),
            @Result(column = "password_id", property = "password", javaType = PasswordDao.class,
                    one = @One(select = "restservice.repositories.PasswordDao.getById"))
    })
    User getByLogin(final String login);

    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "user_base_id", property = "userBase", javaType = UserBase.class,
                    one = @One(select = "restservice.repositories.UserBaseDao.getById")),
            @Result(column = "password_id", property = "password", javaType = PasswordDao.class,
                    one = @One(select = "restservice.repositories.PasswordDao.getById"))
    })
    User getById(final String id);

    @Insert(INSERT)
    void insert(User user);
}
