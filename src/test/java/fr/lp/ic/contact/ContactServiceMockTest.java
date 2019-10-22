package fr.lp.ic.contact;

import fr.lp.ic.contact.dao.IContactDao;
import fr.lp.ic.contact.exception.ContactException;
import fr.lp.ic.contact.exception.ContactNotFoundException;
import fr.lp.ic.contact.model.Contact;

import org.easymock.Mock;

import java.util.Optional;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

public class ContactServiceMockTest extends BaseMockTest{
	
	public static final String VALID_PHONE_NUMBER = "0234564738";
	public static final String VALID_EMAIL = "test@yopmail.fr";
	
	@TestSubject
	ContactService contactService = new ContactService();
	
	@Mock
	IContactDao contactDao;
	
	@Test(expected = ContactException.class)
	public void shouldFailIfNamealreadyExists() throws ContactException {
		String name ="Arnaud";
		
		//Enregistrement des comportements
		expect(contactDao.findByName(name)).andReturn(Optional.of(new Contact()));
		
		// fin de l'enregistrement 
		//EasyMock.replay(contactDao);
		replayAll();
		
		contactService.newContact(name, VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test
	public void shouldInsertElementExists() throws ContactException {
		String name ="Arnaud";
		
		//Enregistrement des comportements
		expect(contactDao.findByName(name)).andReturn(Optional.empty());
		Capture<Contact> capturedContact = newCapture();
		expect(contactDao.save(capture(capturedContact))).andReturn(true);
		
		// fin de l'enregistrement 
		//EasyMock.replay(contactDao);
		replayAll();
		
		contactService.newContact(name, VALID_PHONE_NUMBER, VALID_EMAIL);
		Contact value = capturedContact.getValue();
		Assert.assertEquals(name, value.getName());
		Assert.assertEquals("Phone error", VALID_PHONE_NUMBER, value.getPhone());
		Assert.assertEquals("email error", VALID_EMAIL, value.getEmail());
		
	}
	
	@Test(expected = ContactNotFoundException.class)
	public void shouldFailDeletionIfNameDoesntExists() throws ContactNotFoundException {
		String name = "Arnaud";
		
		//Enregistrement des comportements
		expect(contactDao.findByName(name)).andReturn(Optional.empty());

		// fin de l'enregistrement 
		//EasyMock.replay(contactDao);
		replayAll();
		contactService.deleteContact(name);
		
	}
	
	@Test(expected = ContactNotFoundException.class)
	public void shouldFailUpdateIfNameDoesntExist() throws ContactNotFoundException, ContactException {
		String name = "Arnaud";
		
		//Enregistrement des comportements
		expect(contactDao.findByName(name)).andReturn(Optional.empty());
		
		// fin de l'enregistrement 
		replayAll();
		
		contactService.updateContact(name, "Kevin", VALID_PHONE_NUMBER, VALID_EMAIL);
	}
			
	@Test
	public void ShouldFailUpdateIfSameName() throws ContactException, ContactNotFoundException {
		String name = "Arnaud";
		String newName = "Arnaud";
		
		//Enregistrement des comportements
		expect(contactDao.findByName(name)).andReturn(Optional.of(new Contact()));
		Capture<Contact> capturedContact =  newCapture();
		expect(contactDao.update(eq(name), capture(capturedContact))).andReturn(true);
		
		// fin de l'enregistrement 
		replayAll();
		
		contactService.updateContact(name, newName, VALID_PHONE_NUMBER, VALID_EMAIL);
		Contact value = capturedContact.getValue();
		Assert.assertEquals(newName, value.getName());
		Assert.assertEquals("Phone error",VALID_PHONE_NUMBER ,value.getPhone());
		Assert.assertEquals("Email error", VALID_EMAIL,value.getEmail());
	}

	
	@Test
	public void ShouldFailUpdateIfNotChangeName() throws ContactNotFoundException, ContactException {
		String name = "Arnaud";
		String newName = "Kevin";
		
		//Enregistrement des comportements
		expect(contactDao.findByName(name)).andReturn(Optional.of(new Contact()));
		Capture<Contact> capturedContact =  newCapture();
		expect(contactDao.update(eq(name), capture(capturedContact))).andReturn(true);
		expect(contactDao.findByName(newName)).andReturn(Optional.empty());
		
		// fin de l'enregistrement 
		replayAll();
		
		contactService.updateContact(name, newName, VALID_PHONE_NUMBER, VALID_EMAIL);
		Contact value = capturedContact.getValue();
		Assert.assertEquals(newName, value.getName());
		Assert.assertEquals("Phone error",VALID_PHONE_NUMBER ,value.getPhone());
		Assert.assertEquals("Email error", VALID_EMAIL,value.getEmail());
	}
	
	@Test(expected = ContactException.class)
	public void ShouldFailUpdateIfNewNameExists() throws ContactException, ContactNotFoundException {
		String name = "Arnaud";
		String newName = "Kevin";
		
		//Enregistrement des comportements
		expect(contactDao.findByName(name)).andReturn(Optional.of(new Contact()));
		Capture<Contact> capturedContact =  newCapture();
		expect(contactDao.update(eq(name), capture(capturedContact))).andReturn(true);
		expect(contactDao.findByName(newName)).andReturn(Optional.of(new Contact()));
		
		// fin de l'enregistrement 
		replayAll();
		
		contactService.updateContact(name, newName, VALID_PHONE_NUMBER, VALID_EMAIL);
	}

}
