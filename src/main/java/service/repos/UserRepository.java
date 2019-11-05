package service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.model.User;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByLogin(String login);
}

//@Repository
//public interface UserRepository extends CrudRepository<User, Long> {
//    User findByUsername(String username);
//}
