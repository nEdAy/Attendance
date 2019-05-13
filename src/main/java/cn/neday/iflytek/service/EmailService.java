package cn.neday.iflytek.service;

import cn.neday.iflytek.util.date.DateStyle;
import cn.neday.iflytek.util.date.DateUtil;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${spring.mail.username}")
    private String username; //读取配置文件中的参数

    private final static int TRY_TIMES_MAX = 10;
    private int tryTimes;

    @Async
    public void sendTemplateMail(String mailAddress, String toUserName, String subject, String message) {
        try {
            Thread.sleep(10 * 1000);
            Date date = new Date();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(mailAddress);
            helper.setSubject(DateUtil.DateToString(date, DateStyle.MM_DD_HH_MM) + "-" + subject);
            // 嵌入静态资源
            // FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/favicon.ico"));
            // helper.addInline("weixin", file);
            // 正文模版填充
            Map<String, Object> model = new HashMap<>();
            model.put("toUserName", toUserName);
            model.put("message", message);
            model.put("time", DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS));
            // 读取 html 模板
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(html, true);
            // 添加副本
            // helper.addAttachment("附件-1.jpg", file);
            // helper.addAttachment("附件-2.jpg", file);
            mailSender.send(mimeMessage);
            logger.info("成功发送邮件给" + toUserName + ",message = " + message);
        } catch (Exception e) {
            logger.error("发送邮件给" + toUserName + "失败,message = " + message, e);
            tryTimes++;
            if (tryTimes < TRY_TIMES_MAX) {
                sendTemplateMail(mailAddress, toUserName, subject, message);
            } else {
                e.printStackTrace();
            }
        }
    }

}
