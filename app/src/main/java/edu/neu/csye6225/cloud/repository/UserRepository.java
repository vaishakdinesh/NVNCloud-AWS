package edu.neu.csye6225.cloud.repository;

import edu.neu.csye6225.cloud.modal.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User,Integer> {

    User findUserByEmail(String email);
    User save(User user);
}
