package com.clinica.api.client;

import com.clinica.api.exceptions.InvalidArgumentException;

public enum Gender {
    MALE,
    FEMALE,
    NOT_SPECIFIED;

    public static Gender getGender(String gender) {
        if (gender != null) {
            try {
                return Gender.valueOf(gender.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidArgumentException("Invalid gender, " + gender);
            }
        }
        return Gender.NOT_SPECIFIED;
    }
}
