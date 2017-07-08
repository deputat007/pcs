package restservice.repositories;

import com.zeroisbiggerthanone.pcs.entities.Password;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordDao {

    String SELECT_BY_ID = "SELECT id, password FROM tbl_password WHERE id = #{id} AND deleted IS NULL;";
    String INSERT = "INSERT INTO tbl_password (id, password) VALUES(#{id}, #{password});";

    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "password", property = "password")
    })
    Password getById(final String id);

    @Insert(INSERT)
    void insert(Password password);
}
