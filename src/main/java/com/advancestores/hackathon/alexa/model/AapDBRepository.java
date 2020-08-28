package com.advancestores.hackathon.alexa.model;

// import java.util.List;

// import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface AapDBRepository extends MongoRepository {

//     // @Query(value = "{'employees.name': ?0}", fields = "{'employees' : 0}")

//     AlexaUser findAlexaUserAlexaUserId(String empName);

//     AlexaUser findAlexaUserBySpeedPerksPhone(String name);
// }

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AapDBRepository extends MongoRepository<AlexaUser, String> {
    AlexaUser findByAlexaUserId(String alexaUserId);

}