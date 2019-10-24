package service.repos;

import org.springframework.data.repository.CrudRepository;
import service.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
