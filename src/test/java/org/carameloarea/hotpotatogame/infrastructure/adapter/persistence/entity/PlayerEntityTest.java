package org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerEntityTest {

    @Test
    @DisplayName("It should correctly set and get all properties")
    void testGettersAndSetters() {
        PlayerEntity player = new PlayerEntity();
        player.setId(1);
        player.setNickname("genericPlayer");
        player.setEmail("generic@player.com");
        player.setPassword("pass1234");
        player.setIcon("BLUE");

        assertThat(player.getId()).isEqualTo(1);
        assertThat(player.getNickname()).isEqualTo("genericPlayer");
        assertThat(player.getEmail()).isEqualTo("generic@player.com");
        assertThat(player.getPassword()).isEqualTo("pass1234");
        assertThat(player.getIcon()).isEqualTo("BLUE");
    }

    @Test
    @DisplayName("It should support fluent API for setting properties")
    void testFluentApi() {
        PlayerEntity player = new PlayerEntity()
                .id(1)
                .nickname("genericPlayer")
                .email("generic@player.com")
                .password("pass1234")
                .icon("BLUE");

        assertThat(player.getId()).isEqualTo(1);
        assertThat(player.getNickname()).isEqualTo("genericPlayer");
        assertThat(player.getEmail()).isEqualTo("generic@player.com");
        assertThat(player.getPassword()).isEqualTo("pass1234");
        assertThat(player.getIcon()).isEqualTo("BLUE");
    }

    @Test
    @DisplayName("It should correctly implement equals and hashCode")
    void testEqualsAndHashCode() {
        PlayerEntity player1 = new PlayerEntity().id(1);
        PlayerEntity player2 = new PlayerEntity().id(1);
        PlayerEntity player3 = new PlayerEntity().id(2);
        PlayerEntity player4 = new PlayerEntity();

        assertThat(player1)
                .isEqualTo(player1)
                .isEqualTo(player2)
                .isNotEqualTo(player3)
                .isNotEqualTo(null)
                .isNotEqualTo(new Object())
                .isNotEqualTo(player4);

        assertThat(player2).isEqualTo(player1);
        assertThat(player4).isNotEqualTo(player1);

        assertThat(player1).hasSameHashCodeAs(player2);

        assertThat(player1.hashCode()).isNotEqualTo(player3.hashCode());
    }

    @Test
    @DisplayName("It should return a non-empty string for toString method")
    void testToString() {
        PlayerEntity player = new PlayerEntity()
                .id(1)
                .nickname("genericPlayer")
                .email("generic@player.com")
                .password("pass1234")
                .icon("BLUE");

        String toStringResult = player.toString();

        assertThat(toStringResult)
                .isNotNull()
                .contains("id=1", "nickname='genericPlayer'", "email='generic@player.com'");
    }
}