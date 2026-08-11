package model.entity;

import model.entity.abstr.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Session extends BaseEntity {

    private Long userId;
    private LocalDateTime expirationDate;

    public Session(Long userId, LocalDateTime expirationDate) {
        setUserId(userId);
        setExpirationDate(expirationDate);
    }

    public Session(Long id, boolean isDeleted, Long userId, LocalDateTime expirationDate) {
        super(id, isDeleted);
        setUserId(userId);
        setExpirationDate(expirationDate);
    }

    private void setUserId(Long userId){
        if (userId == null)
            throw new IllegalArgumentException(
                    "userId can't be null"
            );
        this.userId = userId;
    }

    private void setExpirationDate(LocalDateTime expirationDate){
        if (expirationDate == null)
            throw new IllegalArgumentException(
                    "expirationDate can't be null"
            );
        this.expirationDate = expirationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
