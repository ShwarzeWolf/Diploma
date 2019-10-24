package service.repos;

import org.springframework.data.repository.CrudRepository;
import service.domain.Messages;

import java.util.List;

public interface MessageRepo extends CrudRepository<Messages, Long> {

//    List<Messages> findByTag(String tag);

}
