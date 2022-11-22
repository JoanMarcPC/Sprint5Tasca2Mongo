package cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.concurrent.ThreadLocalRandom;

@Getter


public class Throw {
    private int dice1;
    private int dice2;
    private boolean win;

    public Throw() {
        this.dice1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        this.dice2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        if (dice1 + dice2 == 7) {
            this.win = true;
        } else {
            this.win = false;
        }
    }
}
