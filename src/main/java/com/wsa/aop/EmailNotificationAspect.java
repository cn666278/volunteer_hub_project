package com.wsa.aop;

import com.wsa.model.User;
import com.wsa.model.Volunteer;
import com.wsa.model.VolunteerInfo;
import com.wsa.service.UserService;
import com.wsa.service.VolunteerService;
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
    public void sendNotificationEmail(Long id, String email,Long eventId, String status) {
        // 创建邮件内容
        String subject = "Event Registration Status";
        String message = "Dear Volunteer, \n\nYour event registration has been " +
                status +
                ".\n\nThank you.";

        // 发送邮件
        sendEmail(email,eventId, subject, message);
    }

    private void sendEmail(String to, Long eventId,String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("13253348930@163.com");  // 发件人邮箱
            message.setTo(to);  // 收件人邮箱
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            User user = userService.getUserByEmail(to);
            Volunteer volunteer = volunteerService.getVolunteerByUserId(user.getId());
            VolunteerInfo volunteerInfo = new VolunteerInfo();
            volunteerInfo.setVolunteerId(volunteer.getId().intValue());
            volunteerInfo.setInfoTitle(subject);
            volunteerInfo.setInfoBody(text);
            volunteerInfo.setEventId(eventId.intValue());
            userService.addVolunteerInfo(volunteerInfo);
        } catch (MailSendException e) {
            // 打印异常详细信息
            e.printStackTrace();
            // 你可以添加日志或进行进一步的处理
        }
    }

}
