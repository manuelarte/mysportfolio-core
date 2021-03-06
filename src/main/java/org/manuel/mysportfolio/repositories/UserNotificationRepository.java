package org.manuel.mysportfolio.repositories;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends CrudRepository<UserNotification, ObjectId> {

  Page<UserNotification> findAllByToIsAndStatusIsNull(Pageable pageable, String to);

  Optional<UserNotification> findByIdIsAndStatusIsNull(ObjectId objectId);

}
