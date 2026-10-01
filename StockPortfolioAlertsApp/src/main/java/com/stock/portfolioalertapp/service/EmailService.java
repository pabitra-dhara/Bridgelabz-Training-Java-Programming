package com.stock.portfolioalertapp.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.mail.enabled", havingValue = "true")
public class EmailService {
    private final JavaMailSender sender;

    public void sendRegistration(String to, String name) throws Exception {
        MimeMessage m = sender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(m, true);
        h.setTo(to);
        h.setSubject("Welcome to Stock Portfolio Alerts");
        h.setText("Hello " + name + ",\n\nYour Stock Portfolio Alerts account has been created successfully.\n\nYou can now log in and manage your stocks, portfolios, watchlists and price alerts.\n\nRegards,\nStock Portfolio Alerts");
        sender.send(m);
    }

    public void sendPriceAlert(String to, String name, String symbol, String type, String target, String current) throws Exception {
        MimeMessage m = sender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(m, true);
        h.setTo(to);
        h.setSubject("Stock price alert: " + symbol);
        h.setText("Hello " + name + ",\n\nYour " + type + " target for " + symbol + " has been reached.\nTarget price: " + target + "\nCurrent price: " + current + "\n\nRegards,\nStock Portfolio Alerts");
        sender.send(m);
    }
}
