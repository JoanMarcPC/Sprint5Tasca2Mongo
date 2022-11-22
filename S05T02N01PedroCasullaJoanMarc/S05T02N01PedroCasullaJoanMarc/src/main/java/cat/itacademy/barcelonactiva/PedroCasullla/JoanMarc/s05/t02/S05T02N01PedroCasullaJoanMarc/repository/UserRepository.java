package cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.repository;

import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain.Throw;
import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.domain.User;
import cat.itacademy.barcelonactiva.PedroCasullla.JoanMarc.s05.t02.S05T02N01PedroCasullaJoanMarc.dto.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByName(String name);

    @Query("{id:?0},{success:1}") // no caldria {id}
    double findSuccessByUserId(String id);
    @Query(fields = "{name:1,success:1}")
    User findFirstByOrderBySuccessDesc();
@Query(fields = "{name:1,success:1}")
    User findFirstByOrderBySuccessAsc();
    @Query("{},{success:1}") // controlo quines dades rebo
    List<User> findAllSuccess();



}
