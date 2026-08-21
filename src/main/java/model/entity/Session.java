package model.entity;

import model.entity.abstr.BaseEntity;

import java.time.LocalDateTime;

public class Session extends BaseEntity{

    private Long userId;
    private LocalDateTime expirationDate;

    private User user;

    public static Session createNew(Long userId, LocalDateTime expirationDate){
        return new Session(userId, expirationDate);
    }

    public static Session loadFromDb(Long id, boolean isDeleted, Long userId, LocalDateTime expirationDate,
                                     User user){
        return new Session(id, isDeleted, userId, expirationDate, user);
    }

    private Session(Long userId, LocalDateTime expirationDate) {
        setUserId(userId);
        setExpirationDate(expirationDate);
    }

    private Session(Long id, boolean isDeleted, Long userId, LocalDateTime expirationDate, User user) {
        super(id, isDeleted);
        setUserId(userId);
        setExpirationDate(expirationDate);
        setUser(user);
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

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }
}
