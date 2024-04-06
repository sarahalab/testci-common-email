package org.apache.commons.mail;
import org.junit. Test;
import org.subethamail.smtp.server.Session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

public class EmailTest {
	
	private static final String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd" };
	private static final String TEST_HEADER_NAME = "ImportantMessage";
	private static final String TEST_HEADER_VALUE = "ImportantValue";
	private static final String TEST_REPLY = "This is a reply test";
	private static final String TEST_SUBJECT = "This is a subject test";
	
		private String[] testValidChars = {" ", "a", "A", "\uc5ec", "0123456789", "012345678901234567890", "\n" };
		
		//Concrete Email class for testing
		private EmailConcrete email;
		
		@Before
		public void setUpEmailTest() throws Exception
		{
			email = new EmailConcrete();
		//	mockSmptServer = new MockSmtpServer(EmailConfiguration.MOCK_SMTP_PORT);
			
		}
		
		@After
		public void tearDownEmailTest() throws Exception
		{
			email = null; //clear email object
		}
		
		//test that 3 BCCs are added to an email, should we pass in 3 different email addresses as pretend BCCs
		@Test
		public void testAddBcc() throws Exception
		{
			email.addBcc(TEST_EMAILS);
			assertEquals(3, email.getBccAddresses().size());
		}
		
		
		//test that 3 CCs are added to an email, should we pass in 3 different email addresses as pretend CCs
		@Test
		public void testAddCc() throws Exception
		{
			//this is a test edit to push to git
			email.addCc(TEST_EMAILS);
			assertEquals(3, email.getCcAddresses().size());
		}
		
		//test that a header added to an email is the same header that is retrieved (successfully established)
		@Test
		public void testAddHeader() throws Exception
		{
			email.addHeader(TEST_HEADER_NAME, TEST_HEADER_VALUE);
			assertEquals(TEST_HEADER_VALUE, email.getHeaders().get(TEST_HEADER_NAME));
		}
		
		//test that when a header is not established (null), an exception is handled per design of the addHeader function
		@Test (expected = IllegalArgumentException.class)
		public void testAddHeaderDoesNotExist() throws Exception
		{
			email.addHeader(null, null);
			assertNull(email.getHeaders());
		}
		
		
		//test if a reply associated with an email matches the email that is stored in list
		@Test
		public void testAddReplyTo() throws Exception
		{
			email.addReplyTo(TEST_EMAILS[0]);
			 List<InternetAddress> replyToAddresses = email.getReplyToAddresses();
			  assertEquals(TEST_EMAILS[0], replyToAddresses.get(0).getAddress());
		}

		//USES MOCKITO plug-in
		//Test that an exception is thrown if a MimeMessage has already been built
		@Test(expected = IllegalStateException.class)
		public void testBuildMimeMessageAlreadyExists() throws Exception
		{
			email.message = mock(MimeMessage.class);
			email.buildMimeMessage();
		}
		
		/*
		 //Tests building a new MimeMessage 
		@Test
		public void testBuildMimeMessageDoesNotExist() throws Exception
		{
			email.setMsg(null);
			email.buildMimeMessage();
			assertNotNull(email.getMimeMessage());
		}
		*/
		
		//Test that a subject may be established when building mime message
		@Test
		public void testBuildMimeMessageSubject() throws Exception
		{
			
		    email.setSubject(TEST_SUBJECT);
		 //   email.buildMimeMessage();
		    assertEquals(TEST_SUBJECT, email.getSubject());
		}
		
		//Test that a host name is established and can be found when it exists
		@Test
		public void testGetHostNameExists()
		{
			email.setHostName("hostname");
			assertEquals("hostname", email.getHostName());
			
		}
		
		//Test that null is returned (per design of the getHostName function) when no host name is established
		@Test
		public void testGetHostNameDoesNotExist()
		{
			email.setHostName(null);
			assertNull(email.getHostName());
			
		}
		
		//Test that a session is retrieved when credentials are established
		@Test
		public void testGetMailSessionExists() throws Exception
		{
			email.setHostName("HOST_NAME");
			email.setSmtpPort(001);
			email.setStartTLSEnabled(true);
			
			javax.mail.Session session = email.getMailSession();
			assertNotNull(session);
			
	
		}
		
		//Test that an exception is thrown (per design of the getMailSession function), when a session is established with null credentials
		@Test (expected = EmailException.class)
		public void testGetMailSessionDoesNotExist() throws Exception
		{
			email.setHostName(null);
			javax.mail.Session session = email.getMailSession();
			
			assertNull(email.getMailSession());
		}
		
		//Test that when a new date is not established (null), the getSentDate function will instantiate a new date
		@Test
		public void testGetSentDateDoesNotExist()
		{
			email.setSentDate(null);
			
			assertNotNull(email.getSentDate());
		}
		
		//Tests that when a date is established, the getSentDate function does retrieve and use that date
		@Test
		public void testGetSentDateExists()
		{
			Date currentDate = new Date();
			email.setSentDate(currentDate);
			
			assertEquals(currentDate.getTime(), email.getSentDate().getTime());
		}
		
		//Tests that when a timeout in milliseconds is established, the getSocketConnectionTimeout function retrieves this value
		@Test
		public void testGetSocketConnectionTimeout()
		{
			email.setSocketConnectionTimeout(100);
			assertEquals(100, email.getSocketConnectionTimeout());
			
		}
		
		//Tests that an Internet address is established (not null) using the setFrom function
		@Test
		public void testSetFrom() throws Exception
		{
			email.setFrom(TEST_EMAILS[0], "NAME", "UTF-8" );
			
			InternetAddress address = email.getFromAddress();
			assertNotNull(address);
		}
		
		
}
