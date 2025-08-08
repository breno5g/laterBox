package dev.breno5g.laterbox.link.domain.entity;

import dev.breno5g.laterbox.tag.domain.entity.Tag;
import dev.breno5g.laterbox.user.domain.entity.User;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID  id;

    private String url;
    private String title;
    private String description;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "is_favorite")
    private Boolean isFavorite;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Timestamp
    private LocalDateTime readAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "link_tag",
        joinColumns = @JoinColumn(name = "link_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
