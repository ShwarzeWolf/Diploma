package service.dal.dao;

import java.sql.SQLException;

import service.dal.entity.Entity;
import service.dal.searchrequest.SearchRequest;

// import service.dal.dao.SearchRequest.SearchRequest;
// import service.dal.entity.Entity;

public interface DAO<T extends Entity> {
	// void update(T oldDTO, T newDTO) throws SQLException;
	void insert(T entity) throws SQLException;
	// void delete(T entity) throws SQLException;
 	// T[] get(SearchRequest<T> sr) throws SQLException;
	T getOne(SearchRequest<T> sr) throws SQLException;
}