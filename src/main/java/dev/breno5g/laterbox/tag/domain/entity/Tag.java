package dev.breno5g.laterbox.tag.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.breno5g.laterbox.link.domain.entity.Link;
import dev.breno5g.laterbox.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "link_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Link> links;
}
