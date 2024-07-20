package com.getreadyforthenext.core.user.entities;

import com.getreadyforthenext.core.user.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    private String authenticationProvider;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
