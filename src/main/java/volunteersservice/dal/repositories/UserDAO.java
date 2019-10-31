package volunteersservice.dal.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.User;

@Repository
public interface UserDAO {
    public User getUserByID(int id);
    public User getUserByEmail(String email);
    public void save(User user);
    public void update(User user);
    public void delete(User user);
    public List<User> findAll();
}
