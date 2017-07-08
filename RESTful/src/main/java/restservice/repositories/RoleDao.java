package restservice.repositories;

import com.zeroisbiggerthanone.pcs.entities.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao {

    String SELECT_BY_ID = "SELECT id, role_name FROM tbl_role WHERE id = #{id} AND deleted IS NULL;";
    String SELECT_BY_ROLE_NAME = "SELECT id, role_name FROM tbl_role WHERE role_name = #{roleName} AND deleted IS NULL;";
    String INSERT = "INSERT INTO tbl_role (id, role_name) VALUES (#{id}, #{roleName});";

    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "role_name", property = "roleName")
    })
    Role getById(final String id);

    @Select(SELECT_BY_ROLE_NAME)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "role_name", property = "roleName")
    })
    Role getByRoleName(final String roleName);

    @Insert(INSERT)
    void insert(Role role);
}
