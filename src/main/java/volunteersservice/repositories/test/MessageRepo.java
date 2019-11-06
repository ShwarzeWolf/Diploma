package volunteersservice.repositories.test;

import org.springframework.data.repository.CrudRepository;
import volunteersservice.models.Messages;

public interface MessageRepo extends CrudRepository<Messages, Long> {

//    List<Messages> findByTag(String tag);

}
