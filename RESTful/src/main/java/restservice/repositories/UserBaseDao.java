package restservice.repositories;

import com.zeroisbiggerthanone.pcs.entities.Code;
import com.zeroisbiggerthanone.pcs.entities.Role;
import com.zeroisbiggerthanone.pcs.entities.UserBase;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBaseDao {

    String SELECT_BY_ID = "SELECT id, role_id, login, phone_number, secret_digit FROM tbl_user_base WHERE id = #{id} " +
            "AND deleted IS NULL;";
    String INSERT = "INSERT INTO tbl_user_base (id, role_id, login, phone_number, secret_digit) " +
            "VALUES (#{id}, #{role.id}, #{login}, #{phoneNumber}, #{secretDigit});";

    String ADD_SMS_CODE = "UPDATE tbl_user_base SET sms_code = #{param1.code} WHERE id = #{param2} AND deleted IS NULL";
    String DELETE_SMS_CODE = "UPDATE tbl_user_base SET sms_code = NULL WHERE id = #{id} AND deleted IS NULL";
    String GET_SMS_CODE = "SELECT sms_code FROM tbl_user_base WHERE id = #{id} AND deleted IS NULL;";

    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "role_id", property = "role", javaType = Role.class,
                    one = @One(select = "restservice.repositories.RoleDao.getById")),
            @Result(column = "login", property = "login"),
            @Result(column = "phone_number", property = "phoneNumber"),
            @Result(column = "secret_digit", property = "secretDigit"),
    })
    UserBase getById(final String id);

    @Insert(INSERT)
    void insert(final UserBase userBase);

    @Update(ADD_SMS_CODE)
    void addSmsCode(Code code, String id);

    @Update(DELETE_SMS_CODE)
    void deleteSmsCode(final String id);

    @Select(GET_SMS_CODE)
    String getSmsCode(final String id);
}
