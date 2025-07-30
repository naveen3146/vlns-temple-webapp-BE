package vlns.templeweb.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class TempleEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String templeName;
    private String title;
    private String description;
    private LocalDate date;
    private String location;
    private String   contact;
    @Column(name = "imageUrl")
    private String imageUrl;

    public TempleEvents() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTempleName() {
        return templeName;
    }

    public void setTempleName(String templeName) {
        this.templeName = templeName;
    }
}
