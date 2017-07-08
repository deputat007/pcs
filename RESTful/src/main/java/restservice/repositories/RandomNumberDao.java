package restservice.repositories;

import com.zeroisbiggerthanone.pcs.entities.RandomNumber;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomNumberDao {

    String SELECT = "SELECT number FROM tbl_random_number WHERE number = #{number} AND deleted IS NULL;";
    String INSERT = "INSERT INTO tbl_random_number (id, number) VALUES(#{id}, #{number});";
    String DELETE = "UPDATE tbl_random_number SET deleted = CURRENT_TIMESTAMP() WHERE number = #{number}";

    @Select(SELECT)
    int getNumber(int number);

    @Insert(INSERT)
    void insert(RandomNumber number);

    @Update(DELETE)
    void delete(int number);
}
