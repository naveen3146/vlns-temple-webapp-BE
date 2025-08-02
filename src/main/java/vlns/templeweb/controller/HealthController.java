package vlns.templeweb.controller;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @GetMapping("/health")
    public ResponseEntity<HealthStatus> health() {
        logger.info("Health check endpoint called");
        return ResponseEntity.ok(new HealthStatus("OK"));
    }

    @GetMapping("/")
    public ResponseEntity<HealthStatus> rootHealth() {
        logger.info("root Health check endpoint called");
        return ResponseEntity.ok(new HealthStatus("OK"));
    }

    // Simple DTO for health status
    @Setter
    @Getter
    public static class HealthStatus {
        private String status;

        public HealthStatus(String status) {
            this.status = status;
        }
    }

}
