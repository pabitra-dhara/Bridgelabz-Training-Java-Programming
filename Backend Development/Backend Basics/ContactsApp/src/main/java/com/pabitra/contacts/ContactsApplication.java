package com.pabitra.contacts;

import com.pabitra.contacts.config.AppConfig;
import com.pabitra.contacts.model.Contact;
import com.pabitra.contacts.service.ContactService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContactsApplication {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ContactService service = context.getBean(ContactService.class);

        Contact contact = new Contact(1L, "Pabitra", "9876543210", "pabitra@gmail.com");

        service.addContact(contact);

        System.out.println(service.getAllContacts());
    }
}