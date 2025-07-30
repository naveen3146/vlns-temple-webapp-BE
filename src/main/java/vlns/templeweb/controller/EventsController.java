package vlns.templeweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.dto.Events;
import vlns.templeweb.service.EventService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    private EventService eventService;

    //creating the event
    @PostMapping
    public ResponseEntity<Events> createEvent(
            @ModelAttribute Events event,
            @RequestParam(name = "image" , required = false) MultipartFile imageFile) throws IOException {
        Events savedEvent = eventService.saveEvent(event, imageFile);
        return ResponseEntity.status(201).body(savedEvent);
    }

    //fetch the all the existing events
    @GetMapping
    public ResponseEntity<List<Events>> getEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    //update the event
    @PutMapping("/{id}")
    public ResponseEntity<Events> updateEvent(
            @PathVariable Long id,
            @ModelAttribute Events event) {
        logger.info("received the event for update {} {}", event, id);
        Events updatedEvent = eventService.updateEvent(id, event);
        return ResponseEntity.ok(updatedEvent);
    }

    //fetch the all the existing events
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
