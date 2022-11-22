package cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.service;

import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain.User;
import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.dto.UserDTO;
import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.repository.UserRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public List<UserDTO> findAll() {

        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTOS = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            usersDTOS.add(this.EntityToDTO(users.get(i)));

        }
        return usersDTOS;

    }

    @Override
    public Optional<UserDTO> findById(String id) {
        Optional<User> user = userRepository.findById(id);
        Optional<UserDTO> userDTO = Optional.empty();
        if (user.isPresent()) {
            userDTO = Optional.of(EntityToDTO(user.get()));
        }
        return userDTO;
    }

    @Override
    public Optional<UserDTO> add(User user) {
        Optional<UserDTO> userDTO = Optional.empty();
        if (user.getName().equalsIgnoreCase("")) {
            user.setName("anonymous");
        }

        if (this.existByName(user.getName()) && !user.getName().equalsIgnoreCase("anonymous")) {
            userDTO = Optional.empty();
        } else {
            userDTO = Optional.of(this.EntityToDTO(userRepository.save(user)));

        }
        return userDTO;
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);

    }

    @Override
    public Optional<UserDTO> updateById(String id, User user) {
        Optional<UserDTO> userDTO = Optional.empty();
        try {

            Optional<User> userFound = userRepository.findById(id);

            if (userFound.isPresent()) {
                User _user = userFound.get();
                _user.setName(user.getName());
                _user.setPass(user.getPass());
                userDTO = Optional.of(this.EntityToDTO(userRepository.save(_user)));

            }
        } catch (Exception e) {
            userDTO = Optional.empty();
        }
        return userDTO;
    }

    @Override
    public boolean existByName(String name) {
        /*ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase());

        Example<User> example = Example.of(user, modelMatcher);
        return userRepository.exists(example);
         */
        return userRepository.existsByName(name);
    }

    @Override
    public UserDTO EntityToDTO(@NotNull User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDate(entity.getDate());
        dto.setSuccess(entity.getSuccess());
        dto.set_throws(entity.get_throws());
        return dto;

    }

    @Override
    public User DTOtoEntity(@NotNull UserDTO dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setPass(dto.getPass());
        entity.setSuccess(dto.getSuccess());
        entity.set_throws(dto.get_throws());

        return entity;
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<UserDTO> findMaxSuccess() {
        return Optional.of(this.EntityToDTO(userRepository.findFirstByOrderBySuccessDesc()));
    }

    @Override
    public Optional<UserDTO> findMinSuccess() {
        return Optional.of(this.EntityToDTO(userRepository.findFirstByOrderBySuccessAsc()));
    }

    @Override
    public double findAllSuccess() {
        List<User> users = userRepository.findAllSuccess();
        double average = 0;

        if (users.isEmpty()){
            average = -1;
        }
        for (int i = 0; i < users.size(); i++) {
            average += users.get(i).getSuccess();

        }
        average /= users.size();
        return average;

    }
}



