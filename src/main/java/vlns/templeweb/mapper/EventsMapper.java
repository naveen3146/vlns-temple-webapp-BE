package vlns.templeweb.mapper;

import vlns.templeweb.dto.Events;
import vlns.templeweb.model.TempleEvents;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EventsMapper {
    public static TempleEvents toEntity(Events dto) {
        TempleEvents entity = new TempleEvents();
        entity.setId(dto.getEventId());
        entity.setTempleName(dto.getTempleName());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        // Try parsing with ISO format first, fallback to custom format if needed
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dto.getDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            dateTime = LocalDateTime.parse(dto.getDate(), DateTimeFormatter.ofPattern("dd MMMM, yyyy, hh:mm a"));
        }
        entity.setDate(dateTime.toLocalDate());
        entity.setLocation(dto.getLocation());
        entity.setContact(dto.getContact());
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }

    public static Events toDto(TempleEvents entity) {
        Events dto = new Events();
        dto.setEventId(entity.getId());
        dto.setTempleName(entity.getTempleName());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        // Convert LocalDate to String, fallback to ISO format if needed
        try {
            dto.setDate(entity.getDate().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy, hh:mm a")));
        } catch (Exception e) {
            dto.setDate(entity.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        dto.setLocation(entity.getLocation());
        dto.setContact(entity.getContact());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }

    public static List<Events> toDto(List<TempleEvents> templeEvents) {
        return templeEvents.stream()
                .map(EventsMapper::toDto)
                .toList();
    }
}