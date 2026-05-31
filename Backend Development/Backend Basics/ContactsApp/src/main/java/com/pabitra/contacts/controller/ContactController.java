package com.pabitra.contacts.controller;

import com.pabitra.contacts.model.Contact;
import com.pabitra.contacts.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService service;

    @GetMapping
    public List<Contact> getAll() {
        return service.getAllContacts();
    }

    @PostMapping
    public String addContact(
            @RequestBody Contact contact) {

        service.addContact(contact);

        return "Contact Added";
    }

    @GetMapping("/{id}")
    public Contact getById(
            @PathVariable Long id) {

        return service.getContact(id);
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id) {

        service.deleteContact(id);

        return "Deleted Successfully";
    }
}