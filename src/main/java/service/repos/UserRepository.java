package service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
