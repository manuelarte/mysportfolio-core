package org.manuel.mysportfolio.model.notifications;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class NotificationsFactory {

  public static Message createCompetitionsReminderNotification(final String registrationToken,
      final Competition competition) {
    final String notificationBody = String
        .format("Did you play today?, don't forget to register your match for %s",
            competition.getName());

    return Message.builder()
        .setNotification(Notification.builder()
            .setTitle("Competition day")
            .setBody(notificationBody)
            .build())
        .setToken(registrationToken)
        .putData("type", NotificationType.COMPETITION_DAY.toString())
        .putData("competitionId", competition.getId().toString())
        .putData("sport", competition.getSport().toString())
        .setAndroidConfig(AndroidConfig.builder()
            .setTtl(3600 * 1000)
            .build())
        .build();

  }

  @lombok.AllArgsConstructor
  public enum NotificationType {
    COMPETITION_DAY("competition-day"), USER_NOTIFICATION("user-notification");

    final String notificationType;
  }
}
