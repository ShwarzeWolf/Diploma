package volunteersservice.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.UserRole;
import volunteersservice.models.enums.UserRoleEnum;
import volunteersservice.repositories.UserRoleRepository;
import volunteersservice.services.UserRoleService;
import volunteersservice.utils.RepositoryFactory;

@Service
public class UserRoleServiceDefault implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceDefault() {
        this.userRoleRepository = RepositoryFactory.getUserRoleRepository();
    }

    @Override
    public UserRole getRoleByEnum(UserRoleEnum typeEnum) {
        return userRoleRepository.getRoleByEnum(typeEnum);
    }
}
