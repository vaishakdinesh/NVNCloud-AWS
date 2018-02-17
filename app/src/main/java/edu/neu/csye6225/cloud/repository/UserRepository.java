package edu.neu.csye6225.cloud.repository;

import edu.neu.csye6225.cloud.modal.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User,Integer> {

    User findUserByEmail(String email);
    User findUserByUserId(int id);
    User findUserByToken(String token);
    User save(User user);
    User findDistinctByToken(String token);
}
