package com.jalin.contactwebapp.service;


import com.jalin.contactwebapp.pojo.Contact;
import com.jalin.contactwebapp.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact getContactById(String id) {
        return contactRepository.getContact(findIndexById(id));
    }

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.saveContact(contact);
    }

    @Override
    public void updateContact(String id, Contact contact) {
        contactRepository.updateContact(findIndexById(id), contact);
    }

    @Override
    public void deleteContact(String id) {
        contactRepository.deleteContact(findIndexById(id)); 
    }

    @Override
    public List<Contact> getContacts() {
        return contactRepository.getContacts();
    }


    private int findIndexById(String id) {
        return IntStream.range(0, contactRepository.getContacts().size())
            .filter(index -> contactRepository.getContacts().get(index).getId().equals(id))
            .findFirst()
            .orElseThrow();
    }

}
