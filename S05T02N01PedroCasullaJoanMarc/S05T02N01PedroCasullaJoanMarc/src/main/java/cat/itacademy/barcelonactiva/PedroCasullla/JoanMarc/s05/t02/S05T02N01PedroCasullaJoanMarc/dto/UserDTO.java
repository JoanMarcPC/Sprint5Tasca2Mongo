package cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.dto;

import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain.Throw;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class UserDTO {

    private String id;
    private String name;
    private LocalDateTime date;
    private double success;
    private ArrayList<Throw> _throws;
    @JsonIgnore
    private String pass;

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
