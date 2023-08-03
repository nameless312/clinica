package com.clinica.api.client;

import com.clinica.api.exceptions.InvalidArgumentException;
import com.clinica.api.testcontainers.AbstractTestcontainers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
public class GenderTest {
    @Test
    void successGender() {
        // Given
        List<String> genders = new ArrayList<>();
        genders.add("male");
        genders.add("female");
        genders.add(null);
        // When

        // Then
        List<Gender> expected = new ArrayList<>();
        expected.add(Gender.MALE);
        expected.add(Gender.FEMALE);
        expected.add(Gender.NOT_SPECIFIED);

        for (int i = 0; i < genders.size(); i++) {
            assertThat(Gender.getGender(genders.get(i))).isEqualTo(expected.get(i));
        }
    }

    @Test
    void failureGender() {
        // Given
        String gender = "dog";
        // When

        // Then
        assertThatThrownBy(() -> {
            Gender.getGender(gender);
        })
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Invalid gender, " + gender);
    }

}