package fr.lp.ic.contact;

import org.junit.Assert;
import org.junit.Test;

import fr.lp.ic.contact.exception.ContactException;


public class ContactServiceTest {
	
	public static final String VALID_NAME = "Anthony";
	public static final String VALID_PHONE_NUMBER = "0225457845";
	public static final String VALID_EMAIL = "test@yopmail.com";	
	private ContactService service = new ContactService();
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailedIfNameLessThanThree() throws ContactException {
		service.newContact("ab", VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailedIfNameGreaterThanForty() throws ContactException {
		service.newContact("abbaehfueirjfhtehauziekrfkdskrfnghjtlyoui", VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailedIfNameLessIsNull() throws ContactException {
		service.newContact(null, VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test(expected=ContactException.class)
	public void shouldFailedIfNameAlreadyExists() throws ContactException {
		service.newContact("Arnaud", VALID_PHONE_NUMBER, VALID_EMAIL);
		service.newContact("Arnaud", VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test
	public void shouldInsertValidContact() throws ContactException {
		service.newContact(VALID_NAME, VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
		
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailedIfPhoneGreaterThanTen() throws ContactException {
		service.newContact(VALID_NAME, "02344658432", VALID_EMAIL);
	}
	
	/*@Test(expected=IllegalArgumentException.class)
	public void shouldFailedIfPhoneNotBeginBy02() throws ContactException {
		service.newContact(VALID_NAME, "03344658432", VALID_EMAIL);
	}*/
}
