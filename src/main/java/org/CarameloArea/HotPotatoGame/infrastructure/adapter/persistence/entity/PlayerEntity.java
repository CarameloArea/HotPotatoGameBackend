package org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "players")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlayerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Setter
    @Getter
    @NotNull
    @Size(max = 100)
    @Column(name = "nickname", length = 100, unique = true)
    private String nickname;

    @Setter
    @Getter
    @NotNull
    @Size(max = 150)
    @Email
    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;

    @Setter
    @Getter
    @NotNull
    @Column(name = "password", length = 150, nullable = false)
    private String password;

    @Setter
    @Getter
    @Column(name = "icon", length = 50)
    private String icon;

    public PlayerEntity id(Integer id) {
        this.setId(id);
        return this;
    }

    public PlayerEntity nickname(String nickname) {
        this.setNickname(nickname);
        return this;
    }

    public PlayerEntity email(String email) {
        this.setEmail(email);
        return this;
    }

    public PlayerEntity password(String password) {
        this.setPassword(password);
        return this;
    }

    public PlayerEntity icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerEntity)) {
            return false;
        }
        return id != null && id.equals(((PlayerEntity) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PlayerEntity{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
