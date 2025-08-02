package vlns.templeweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    private static final Logger logger = LoggerFactory.getLogger(RootController.class);
    @GetMapping("/")
    public ResponseEntity<String> root() {
        logger.info("call received for health check endpoint for method root");
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        logger.info("call received for health check endpoint");
        return ResponseEntity.ok("OK");
    }


}
