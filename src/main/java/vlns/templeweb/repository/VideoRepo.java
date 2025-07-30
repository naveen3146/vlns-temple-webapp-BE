package vlns.templeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlns.templeweb.model.VideoModel;

public interface VideoRepo extends JpaRepository<VideoModel, Long> {
}
