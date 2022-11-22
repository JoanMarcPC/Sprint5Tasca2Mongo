package cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.controller;

import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain.Throw;
import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain.User;
import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.dto.UserDTO;
import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RestController1 {

    @Autowired
    UserServiceImp userServiceImp;

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/user/getAll")
    public ResponseEntity<?> getAllUsers() {
        ResponseEntity rp;

        try {
            List<UserDTO> users = userServiceImp.findAll();
            if (users.isEmpty()) {
                rp = ResponseEntity.status(HttpStatus.NO_CONTENT).body("no hi ha cap usuari per mostrar");

            } else {
                rp = ResponseEntity.status(HttpStatus.OK).body(users);
            }

        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;

    }

    @GetMapping("/user/getOne/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") String id) {
        ResponseEntity rp;
        try {
            Optional<UserDTO> user = userServiceImp.findById(id);

            if (user.isPresent()) {
                rp = ResponseEntity.status(HttpStatus.OK).body(user.get());

            } else {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existeix usuari amb aquesta id");

            }
        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;

    }

    @PostMapping("/user/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        ResponseEntity rp;
        try {
            Optional<UserDTO> _user = userServiceImp.add(new User(user.getName(), user.getPass()));
            if (_user.isEmpty()) {
                rp = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Usuari ja registrat");
            } else {
                rp = ResponseEntity.status(HttpStatus.CREATED).body(_user);
            }
        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;
    }

    @PutMapping("/user/update/{id}")

    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {

        ResponseEntity rp;
        Optional<UserDTO> _user = Optional.empty();

        try {
            if(!userServiceImp.existByName(userDTO.getName())){
                _user = userServiceImp.updateById(id, userServiceImp.DTOtoEntity(userDTO));


                if (_user.isPresent()) {
                    rp = ResponseEntity.status(HttpStatus.OK).body(_user.get());

                } else {
                    rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existeix usuari amb aquesta id");

                }
            }else{
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ja existeix un usuari amb aquest nom");
            }

        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;

    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        ResponseEntity rp;
        try {
            if (userServiceImp.existsById(id)) {
                userServiceImp.deleteById(id);
                rp = ResponseEntity.status(HttpStatus.OK).body("Usuari borrat");
            } else {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existeix usuari amb aquesta id");
            }

        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;
    }


    @PostMapping("/user/{id}/games")
    public ResponseEntity<?> throwDice(@PathVariable("id") String user_id) {
        ResponseEntity rp;

        try {
            Optional<UserDTO> user = userServiceImp.findById(user_id);
            if (user.isEmpty()) {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("no hi ha jugador amb aquesta id");
            } else {
                user.get().addThrow(new Throw());
                rp = ResponseEntity.status(HttpStatus.CREATED).body((userServiceImp.updateById(user_id, userServiceImp.DTOtoEntity(user.get()))));

            }
        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;

    }

    @GetMapping("/user/{id}/games")
    public ResponseEntity<?> showThrows(@PathVariable("id") String user_id) {
        ResponseEntity rp;

        try {
            Optional<UserDTO> user = userServiceImp.findById(user_id);
            if (user.isEmpty()) {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("no hi ha jugador amb aquesta id");
            } else {

                rp = ResponseEntity.status(HttpStatus.OK).body(user.get().get_throws());
            }
        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;

    }

    @DeleteMapping("/user/{id}/games")
    public ResponseEntity<?> deleteThrows(@PathVariable("id") String user_id) {
        ResponseEntity rp;

        try {
            Optional<UserDTO> user = userServiceImp.findById(user_id);
            if (user.isEmpty()) {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("no hi ha jugador amb aquesta id");
            } else if (!user.get().get_throws().isEmpty()) {
                user.get().removeThrows();

                rp = ResponseEntity.status(HttpStatus.OK).body("Tirades borrades");
            } else {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("no hi ha tirades d'aquest judador per esborrar");
            }
        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;

    }


    @GetMapping("/user/rankings")
    public ResponseEntity<?> getAllRankings() {
        ResponseEntity rp;

        try {
            List<UserDTO> users = userServiceImp.findAll();

            if (users.isEmpty()) {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("no hi ha jugadors per mostrar ranking");
            } else {
                HashMap<String, Double> ranking = new HashMap<String, Double>();

                for (UserDTO user : users) {
                    List<Throw> _throws = new ArrayList<>();
                    double wins = 0;
                    for (Throw _throw : _throws) {
                        if (_throw.isWin()) {
                            wins += 1;
                        }

                    }
                    wins = (wins / _throws.size()) * 100;
                    ranking.put(user.getName(), wins);
                }


                rp = ResponseEntity.status(HttpStatus.OK).body(ranking);
            }
        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;

    }

    @GetMapping("/user/ranking")
    public ResponseEntity<?> getRanking() {
        ResponseEntity rp;

        try {
            double average = userServiceImp.findAllSuccess();

            if (average==-1) {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("no hi ha jugadors per mostrar ranking");
            } else {

                rp = ResponseEntity.status(HttpStatus.OK).body(average);
            }
        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;
    }

    @GetMapping("/user/ranking/winner")
    public ResponseEntity<?> getBestRanking() {
        ResponseEntity rp;

        try {
            Optional<UserDTO> user = userServiceImp.findMaxSuccess();

            if (user.isEmpty()) {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("no hi ha jugadors per mostrar ranking");
            } else {
                HashMap<String, Double> winner = new HashMap<String, Double>();
                winner.put(user.get().getName(), user.get().getSuccess());

                rp = ResponseEntity.status(HttpStatus.OK).body(winner);
            }
        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;

    }

    @GetMapping("/user/ranking/loser")
    public ResponseEntity<?> getWorstRanking() {
        ResponseEntity rp;

        try {
            Optional<UserDTO> user = userServiceImp.findMinSuccess();

            if (user.isEmpty()) {
                rp = ResponseEntity.status(HttpStatus.NOT_FOUND).body("no hi ha jugadors per mostrar ranking");
            } else {
                HashMap<String, Double> loser = new HashMap<String, Double>();
                loser.put(user.get().getName(), user.get().getSuccess());

                rp = ResponseEntity.status(HttpStatus.OK).body(loser);
            }
        } catch (Exception e) {
            rp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return rp;
    }

    }


