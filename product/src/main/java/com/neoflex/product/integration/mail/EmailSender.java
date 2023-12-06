package com.neoflex.product.integration.mail;

import com.neoflex.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private final MailProperties mailProperties;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendComplexMessage(String to, String subject, ProductDto productDto) throws MessagingException {
        Context context = getContext(productDto);
        String htmlBody = thymeleafTemplateEngine.process("product-template.html", context);
        sendHtmlMessage(to, subject, htmlBody);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    private Context getContext(ProductDto productDto) {

        Context context = new Context();

        context.setVariable("productName", productDto.getName());
        context.setVariable("productType", productDto.getProductType());
        context.setVariable("productStartDate", productDto.getStartDate());
        context.setVariable("productEndDate", productDto.getEndDate());
        context.setVariable("productDescription", productDto.getDescription());
        context.setVariable("productAuthor", productDto.getAuthor());
        context.setVariable("productVersion", productDto.getVersion());

        context.setVariable("tariffName", productDto.getTariffDto().getName());
        context.setVariable("tariffStartDate", productDto.getTariffDto().getStartDate());
        context.setVariable("tariffEndDate", productDto.getTariffDto().getEndDate());
        context.setVariable("tariffDescription", productDto.getTariffDto().getDescription());
        context.setVariable("tariffRate", productDto.getTariffDto().getRate());
        context.setVariable("tariffAuthor", productDto.getTariffDto().getAuthor());
        context.setVariable("tariffVersion", productDto.getTariffDto().getVersion());

        return context;
    }
}
