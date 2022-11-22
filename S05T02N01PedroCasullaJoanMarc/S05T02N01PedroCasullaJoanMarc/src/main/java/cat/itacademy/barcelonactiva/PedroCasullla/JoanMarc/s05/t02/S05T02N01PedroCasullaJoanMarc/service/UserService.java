package cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.service;

import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain.Throw;
import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain.User;
import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.dto.UserDTO;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Iterable<UserDTO> findAll();

    Optional<UserDTO> findById(String Id);

    Optional<UserDTO> add(User user);

    void deleteById(String id);

    Optional<UserDTO> updateById(String id,User user);

    boolean existByName(String name);

    UserDTO EntityToDTO(User entity);

    User DTOtoEntity (UserDTO dto);

    boolean existsById(String id);

    Optional<UserDTO> findMaxSuccess();

    Optional<UserDTO> findMinSuccess();

    double findAllSuccess();
}
