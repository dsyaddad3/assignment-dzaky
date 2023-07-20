package com.jalin.contactwebapp.service;


import com.jalin.contactwebapp.pojo.Contact;

import java.util.List;

public interface ContactService {
    Contact getContactById(String id);
    Contact saveContact(Contact contact);
    void updateContact(String id, Contact contact);
    void deleteContact(String id);
    List<Contact> getContacts();
}
