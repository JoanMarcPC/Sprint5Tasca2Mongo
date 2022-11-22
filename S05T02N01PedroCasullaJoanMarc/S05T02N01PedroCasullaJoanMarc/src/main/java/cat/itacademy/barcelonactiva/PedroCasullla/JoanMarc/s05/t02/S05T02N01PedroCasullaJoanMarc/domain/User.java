package cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Document(collection = "Dices")
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String name;

    private LocalDateTime date;

    private String pass;

    private double success;

    private ArrayList<Throw> _throws;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        this._throws = new ArrayList<Throw>();
        this.date = LocalDateTime.now();

    }

    public void addThrow(Throw _throw) {
        this._throws.add(_throw);
        double wins = 0;
        for (Throw _throw1 : _throws) {
            if (_throw1.isWin()) {
                wins += 1;
            }
        }
        wins /= this._throws.size();
        this.success = wins*100;
    }
    public void removeThrows (){
        this._throws = new ArrayList<>();
        this.success = 0;
    }

}
