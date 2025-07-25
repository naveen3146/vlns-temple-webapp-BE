package vlns.templeweb.Repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import vlns.templeweb.model.Users;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users,String> {
    Optional<Users>findByUsername(String username);
}
