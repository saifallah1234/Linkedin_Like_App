package Elkhadema.khadema.Service.ServiceInterfaces;

import java.util.List;

import Elkhadema.khadema.domain.Notification;
import Elkhadema.khadema.domain.User;

public interface NotificationService {

    List<Notification> messageNotifications(User user);

    List<Notification> postNotifications(User user);

    List<Notification> adminNotifications(User user);

    List<Notification> jobNotifications(User user);
    List<Notification> allNotifications(User user);
}