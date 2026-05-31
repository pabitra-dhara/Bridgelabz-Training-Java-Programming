package com.pabitra.contacts.service;

import com.pabitra.contacts.model.Contact;
import com.pabitra.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public void addContact(Contact contact) {
        repository.save(contact);
    }

    public Contact getContact(Long id) {
        return repository.findById(id);
    }

    public void deleteContact(Long id) {
        repository.delete(id);
    }
}
