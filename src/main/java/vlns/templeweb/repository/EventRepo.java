package vlns.templeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlns.templeweb.model.TempleEvents;

@Repository
public interface EventRepo extends JpaRepository<TempleEvents, Long> {

}
