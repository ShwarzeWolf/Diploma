package volunteersservice.dal.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.User;

@Repository
public interface UserDAO {
    public User getUserByID(int id);
    public User getUserByEmail(String email);
    public User getUserByLogin(String login);
    public boolean save(User user);
    public boolean update(User user);
    public boolean delete(User user);
    public List<User> findAll();
}
