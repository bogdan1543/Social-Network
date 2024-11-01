package domain;

import java.time.LocalDateTime;

public class Friendship extends Entity<Integer>{
    private LocalDateTime dateTime;
    private Integer idUser1;
    private Integer idUser2;

    public Friendship(Integer idUser1, Integer idUser2) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getIdUser1() {
        return idUser1;
    }

    public Integer getIdUser2() {
        return idUser2;
    }
}
