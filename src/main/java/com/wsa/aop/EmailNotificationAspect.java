package com.wsa.aop;

import com.wsa.model.*;
import com.wsa.service.EventService;
import com.wsa.service.OrganizerService;
import com.wsa.service.UserService;
import com.wsa.service.VolunteerService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmailNotificationAspect {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private EventService eventService;

    @Async
    @AfterReturning(pointcut = "execution(* com.wsa.service.EventService.updateVolunteerStatus(..)) && args(id, email, eventId, status)")
    public void sendNotificationEmail(Long id, String email, Long eventId, String status) {
        User user = userService.getUserByEventRegistrationId(id);
        String subject = "Event Registration Status";
        String message = "Dear Volunteer, \n\nYour event registration has been " +
                status +
                ".\n\nThank you.";

        sendEmail(email, eventId, subject, message, user, false);
    }

    @Async
    @AfterReturning(pointcut = "execution(* com.wsa.service.RewardStoreService.redeemItem(..)) && args(request)", returning = "response")
    public void sendRedeemNotificationEmail(RedeemRequest request, RedeemResponse response) {
        User user = userService.getUserById(request.getUserId());
        String email = user.getEmail();
        String subject = "Redemption Successful";
        String message = "Dear " + user.getUsername() + ", \n\nYour redemption for item was successful.\n\nThank you for your participation.";

        sendEmail(email, null, subject, message, user, false);
    }

    @Async
    @AfterReturning(pointcut = "execution(* com.wsa.service.EventService.subscribeForEvent(..)) && args(eventRegistration)")
    public void sendSubscriptionEmail(EventRegistrations eventRegistration) {
        User user = userService.getUserByVolunteerId(eventRegistration.getVolunteerId());
        Event event = eventService.getEventById(eventRegistration.getEventId());
        String email = user.getEmail();
        String subject = "Event Subscription Successful";
        String message = "Dear " + user.getUsername() + ", \n\nYou have successfully subscribed to " + event.getTitle() + ".\n\nThank you for your participation.";

        sendEmail(email, eventRegistration.getEventId(), subject, message, user, false);
    }

    @Async
    @AfterReturning(pointcut = "execution(* com.wsa.service.EventService.registerForEvent(..)) && args(eventRegistration)")
    public void sendRegistrationEmail(EventRegistrations eventRegistration) {
        Event event = eventService.getEventById(eventRegistration.getEventId());
        User user = userService.getUserById(event.getOrganizerId());
        String email = user.getEmail();
        String subject = "You have received a new registration application";
        String message = "Dear " + user.getUsername() + ", \n\nYou have received a new registration application for " + event.getTitle() + ".\n\nPlease process as soon as possible.";

        sendEmail(email, eventRegistration.getEventId(), subject, message, user, true);
    }

    public void sendEmail(String to, Long eventId, String subject, String text, User user, Boolean isOrganizer) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("13253348930@163.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            if (isOrganizer) return;
            Volunteer volunteer = volunteerService.getVolunteerByUserId(user.getId());
            VolunteerInfo volunteerInfo = new VolunteerInfo();
            volunteerInfo.setVolunteerId(volunteer.getId().intValue());
            volunteerInfo.setInfoTitle(subject);
            volunteerInfo.setInfoBody(text);

            if (eventId != null) {
                volunteerInfo.setEventId(eventId.intValue());
            }

            userService.addVolunteerInfo(volunteerInfo);
        } catch (MailSendException e) {
            e.printStackTrace();
        }
    }
}
