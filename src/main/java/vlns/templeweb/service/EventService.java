package vlns.templeweb.service;

import com.amazonaws.services.s3.AmazonS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.dto.Events;
import vlns.templeweb.mapper.EventsMapper;
import vlns.templeweb.model.TempleEvents;
import vlns.templeweb.repository.EventRepo;
import vlns.templeweb.util.DateTimeUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final AmazonS3 s3Client;

    private final String bucketName;

    private final EventRepo eventRepo;

    public EventService(AmazonS3 s3Client, @Value("${aws.s3.bucket}") String bucketName, EventRepo eventRepo) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.eventRepo = eventRepo;
    }

    public Events saveEvent(Events event, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String key = "events/" + imageFile.getOriginalFilename();
            com.amazonaws.services.s3.model.ObjectMetadata metadata = new com.amazonaws.services.s3.model.ObjectMetadata();
            metadata.setContentLength(imageFile.getSize());
            s3Client.putObject(bucketName, key, imageFile.getInputStream(), metadata);
            event.setImageUrl(key);
        } else {
            event.setImageUrl(null);
        }
        TempleEvents entity = EventsMapper.toEntity(event);
        eventRepo.save(entity);
        return EventsMapper.toDto(entity);
    }

    public List<Events> getAllEvents() {
        List<TempleEvents> templeEvents = eventRepo.findAll();
        List<Events> eventsList = EventsMapper.toDto(templeEvents);
        logger.info("events List from db after dto conversion {}", eventsList);
        for (Events events : eventsList) {
            if(events.getImageUrl()!=null){
                java.util.Date expiration = new java.util.Date(System.currentTimeMillis() + 3600 * 1000); // 1 hour
                String preSignedUrl = s3Client.generatePresignedUrl(bucketName, events.getImageUrl(), expiration).toString();
                events.setImageUrl(preSignedUrl);
            }
        }
        logger.info("final events list before return to controller {}", eventsList);
        return eventsList;
    }

    public Events updateEvent(Long id, Events event) {
        logger.info("event received for update {} {}", event, id);
        Optional<TempleEvents> fetchedEntityOpt = eventRepo.findById(id);
        logger.info("entity fetched from db{}", fetchedEntityOpt);
        TempleEvents entity;
        if (fetchedEntityOpt.isPresent()) {
            entity = fetchedEntityOpt.get();
            entity.setTempleName(event.getTempleName());
            entity.setTitle(event.getTitle());
            entity.setLocation(event.getLocation());
            entity.setDescription(event.getDescription());
            entity.setDate(DateTimeUtil.parseDateTime(event.getDate()));
            entity.setContact(event.getContact());
            entity.setImageUrl(event.getImageUrl());
        } else {
            entity = EventsMapper.toEntity(event);
            entity.setId(id); // Set the ID manually for upsert
        }
        eventRepo.save(entity);
        logger.info("event updated in db successfully {}", event);
        Events updatedEvent = EventsMapper.toDto(entity);
        logger.info("updated event return to controller after save{}", updatedEvent);
        return updatedEvent;
    }

    public void deleteEvent(Long id) {
        logger.info("event received from UI for delete {}", id);
        Optional<TempleEvents> event = eventRepo.findById(id);
        logger.info("event fetched from db for delete {}", event);
        event.ifPresent(eventRepo::delete);
    }

}