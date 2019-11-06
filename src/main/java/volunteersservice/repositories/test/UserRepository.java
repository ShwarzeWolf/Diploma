package volunteersservice.repositories.test;

import org.springframework.data.jpa.repository.JpaRepository;
import volunteersservice.models.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByLogin(String login);
}