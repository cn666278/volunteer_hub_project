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
    private JavaMailSender mailSender; // Used to send emails

    @Autowired
    private UserService userService; // Service for user-related operations

    @Autowired
    private VolunteerService volunteerService; // Service for volunteer-related operations

    // After the volunteer status is updated in the EventService, this method sends an email notification
    @AfterReturning(pointcut = "execution(* com.wsa.service.EventService.updateVolunteerStatus(..)) && args(id, email, eventId, status)")
    public void sendNotificationEmail(Long id, String email, Long eventId, String status) {
        User user = userService.getUserByEventRegistrationId(id); // Retrieve the user by event registration ID
        String subject = "Event Registration Status";
        String message = "Dear Volunteer, \n\nYour event registration has been " +
                status +
                ".\n\nThank you.";

        // Send the notification email
        sendEmail(email, eventId, subject, message, user);
    }

    // After an item is successfully redeemed in the RewardStoreService, this method sends an email notification
    @AfterReturning(pointcut = "execution(* com.wsa.service.RewardStoreService.redeemItem(..)) && args(request)", returning = "response")
    public void sendRedeemNotificationEmail(RedeemRequest request, RedeemResponse response) {
        User user = userService.getUserById(request.getUserId()); // Retrieve the user by ID
        String email = user.getEmail();
        String subject = "Redemption Successful";
        String message = "Dear " + user.getUsername() + ", \n\nYour redemption for the item was successful.\n\nThank you for your participation.";

        // Send the redemption notification email
        sendEmail(email, null, subject, message, user);
    }

    // Utility method to send an email and log the information
    private void sendEmail(String to, Long eventId, String subject, String text, User user) {
        try {
            // Create a new SimpleMailMessage for sending the email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("13253348930@163.com"); // Set the sender's email address
            message.setTo(to); // Set the recipient's email address
            message.setSubject(subject); // Set the email subject
            message.setText(text); // Set the email body

            mailSender.send(message); // Send the email

            // Retrieve the volunteer information associated with the user
            Volunteer volunteer = volunteerService.getVolunteerByUserId(user.getId());
            VolunteerInfo volunteerInfo = new VolunteerInfo();
            volunteerInfo.setVolunteerId(volunteer.getId().intValue());
            volunteerInfo.setInfoTitle(subject);
            volunteerInfo.setInfoBody(text);

            // If an event ID is provided, include it in the volunteer information
            if (eventId != null) {
                volunteerInfo.setEventId(eventId.intValue());
            }

            // Log the email information in the system for future reference
            userService.addVolunteerInfo(volunteerInfo);
        } catch (MailSendException e) {
            // Log the exception in case of email sending failure
            e.printStackTrace();
        }
    }
}
