package model.dto.responce;

import model.vo.ContactInfo;
import model.vo.Id;

import java.time.LocalDate;

public record UserResponseDto(Id id, LocalDate registrationDate, ContactInfo contactInfo, String name) {
}
