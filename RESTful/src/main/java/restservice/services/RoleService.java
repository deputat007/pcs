package restservice.services;

import com.zeroisbiggerthanone.pcs.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restservice.repositories.RoleDao;
import restservice.services.core.IdGenerator;

@Service
public class RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role getById(String id) throws Exception {
        return roleDao.getById(id);
    }

    public String insert(Role role) throws Exception {
        final String id = IdGenerator.generateId();

        role.setId(id);
        roleDao.insert(role);

        return id;
    }

    public Role getByRoleName(String roleName) {
        return roleDao.getByRoleName(roleName);
    }
}
