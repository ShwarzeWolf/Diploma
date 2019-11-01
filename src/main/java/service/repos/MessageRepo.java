package service.repos;

import org.springframework.data.repository.CrudRepository;
import service.model.Messages;

public interface MessageRepo extends CrudRepository<Messages, Long> {

//    List<Messages> findByTag(String tag);

}
