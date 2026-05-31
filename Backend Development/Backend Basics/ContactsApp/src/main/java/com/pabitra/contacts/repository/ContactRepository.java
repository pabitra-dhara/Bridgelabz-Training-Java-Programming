package com.pabitra.contacts.repository;

import com.pabitra.contacts.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactRepository {

    private final List<Contact> contacts = new ArrayList<>();

    public List<Contact> findAll() {
        return contacts;
    }

    public void save(Contact contact) {
        contacts.add(contact);
    }

    public Contact findById(Long id) {
        return contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void delete(Long id) {
        contacts.removeIf(c -> c.getId().equals(id));
    }
}