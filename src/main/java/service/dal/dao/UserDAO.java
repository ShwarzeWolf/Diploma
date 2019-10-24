package service.dal.dao;

import java.util.List;

import service.dal.models.User;

public interface UserDAO {
    public User getUserByID(int id);
    public User getUserByEmail(String email);
    public void save(User user);
    public void update(User user);
    public void delete(User user);
    public List<User> findAll();
}
