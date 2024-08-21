package com.wsa.aop;

import com.wsa.model.User;
import com.wsa.model.Volunteer;
import com.wsa.model.VolunteerInfo;
import com.wsa.service.UserService;
import com.wsa.service.VolunteerService;
import com.wsa.model.RedeemRequest;
import com.wsa.model.RedeemResponse;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    @AfterReturning(pointcut = "execution(* com.wsa.service.EventService.updateVolunteerStatus(..)) && args(id, email, eventId, status)")
    public void sendNotificationEmail(Long id, String email, Long eventId, String status) {
        User user = userService.getUserByEventRegistrationId(id);
        String subject = "Event Registration Status";
        String message = "Dear Volunteer, \n\nYour event registration has been " +
                status +
                ".\n\nThank you.";

        sendEmail(email, eventId, subject, message, user);
    }

    @AfterReturning(pointcut = "execution(* com.wsa.service.RewardStoreService.redeemItem(..)) && args(request)", returning = "response")
    public void sendRedeemNotificationEmail(RedeemRequest request, RedeemResponse response) {
            User user = userService.getUserById(request.getUserId());
            String email = user.getEmail();
            String subject = "Redemption Successful";
            String message = "Dear " + user.getUsername() + ", \n\nYour redemption for item was successful.\n\nThank you for your participation.";

            sendEmail(email, null, subject, message, user);

    }

    private void sendEmail(String to, Long eventId, String subject, String text, User user) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("13253348930@163.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
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
