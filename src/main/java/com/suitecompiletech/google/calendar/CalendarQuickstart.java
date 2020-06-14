/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.suitecompiletech.google.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import net.time4j.PlainDate;
import net.time4j.calendar.ChineseCalendar;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
/**
 * 
 * @author Henry
 *
 */

public class CalendarQuickstart {
	private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	
	public static void main(String... args) throws IOException, GeneralSecurityException, ParseException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();
		 ChronoFormatter<ChineseCalendar> f =
			        ChronoFormatter.ofPattern(
			          "d/M/U(r)", 
			          PatternType.CLDR, 
			          Locale.ROOT, 
			          ChineseCalendar.axis());
		// programmed with help from Jess
		for (int year = 2020; year <= 2050; year++) {
			PlainDate gregorian = f.parse("18/5/ji-hai(" + year + ")").transform(PlainDate.axis());	
			 DateTime dateTime = new DateTime(gregorian.toString() + "T09:00:00-07:00");
			 createEvents(service, dateTime, dateTime);
			 System.out.println(dateTime);
		}
	}
	
	public static void mainGregToLunar(String... args) throws IOException, GeneralSecurityException, ParseException {
		// Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		//Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
			//	.setApplicationName(APPLICATION_NAME).build();
		 ChronoFormatter<ChineseCalendar> f =
			        ChronoFormatter.ofPattern(
			          "d/M/U(r)", 
			          PatternType.CLDR, 
			          Locale.ROOT, 
			          ChineseCalendar.axis());
		 // see https://www.hko.gov.hk/en/gts/time/calendar/pdf/files/2020e.pdf
		 // see https://www.hko.gov.hk/en/gts/time/calendar/pdf/files/2021e.pdf
		 // etc https://www.hko.gov.hk/en/gts/time/calendar/pdf/files/{YEAR}e.pdf

		 
	
		for (int year = 2020; year <= 2050; year++) {
			PlainDate gregorian = f.parse("18/5/ji-hai(" + year + ")").transform(PlainDate.axis());	
			 DateTime dateTime = new DateTime(gregorian.toString() + "T09:00:00-07:00");
			 System.out.println(dateTime);
			
			//PlainDate gregorian = f.parse("18/5/ji-hai(" + year + ")").transform(PlainDate.axis());
			 //System.out.println(gregorian); 
			 /*
			  * 2020-07-08
2021-06-27
2022-06-16
2023-07-05
2024-06-23
2025-06-13
2026-07-02
2027-06-22
2028-06-10
2029-06-29
2030-06-18
2031-07-07
2032-06-25
2033-06-14
2034-07-03
2035-06-23
2036-06-12
2037-07-01
2038-06-20
2039-06-09
2040-06-27
2041-06-16
2042-07-05
2043-06-24
2044-06-13
2045-07-02
2046-06-21
2047-06-11
2048-06-28
2049-06-17

			  */
		 }
		  
		// createEvents(service);
		// listEvents(service);
	}

	private static void createEvents(Calendar service, DateTime startDateTime, DateTime endDateTime) throws IOException {
		Event event = new Event().setSummary("Mum's bday").setLocation("166 w 45th Ave")
				.setDescription("remember to bring food");

		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("America/Vancouver");
		event.setStart(start);

		EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("America/Vancouver");
		event.setEnd(end);

		//String[] recurrence = new String[] { "RRULE:FREQ=DAILY;COUNT=2" };
		//event.setRecurrence(Arrays.asList(recurrence));

		EventAttendee[] attendees = new EventAttendee[] { 
				new EventAttendee().setEmail("q8e192@gmail.com"),
				new EventAttendee().setEmail("jessica.heung@gmail.com")
		};
		event.setAttendees(Arrays.asList(attendees));

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(60 * 24 * 7),
				new EventReminder().setMethod("popup").setMinutes(10), };
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);

		String calendarId = "primary";
		event = service.events().insert(calendarId, event).execute();
		System.out.printf("Event created: %s\n", event.getHtmlLink());
	}

	private static void listEvents(Calendar service) throws IOException {
		// List the next 10 events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary").setMaxResults(100).setTimeMin(now).setOrderBy("startTime")
				.setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				System.out.printf("%s (%s)\n", event.getSummary(), start);
			}
		}
	}
}