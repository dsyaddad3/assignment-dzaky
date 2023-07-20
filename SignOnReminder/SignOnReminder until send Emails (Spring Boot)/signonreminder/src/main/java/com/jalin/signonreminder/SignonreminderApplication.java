package com.jalin.signonreminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@SpringBootApplication
public class SignonreminderApplication {
	@Autowired
	private  EmailSenderService senderService;
	@Autowired
	private GenerateTextService generateTextService;

	public static void main(String[] args) {
		SpringApplication.run(SignonreminderApplication.class, args);

	}

	@EventListener(ApplicationReadyEvent.class)
	public void generateTextAndSendEmail(){
		String pathData = "src\\main\\resources\\input\\data.txt";
//		String recipientEmail = "bluefeline310@gmail.com";
		HashMap<String,String> recipientEmails = new HashMap<>();

		//load email
		recipientEmails.put("NOB","bluefeline310@gmail.com");
		recipientEmails.put("BSR","bluefeline310@gmail.com");
		recipientEmails.put("BCA","bluefeline310@gmail.com");
		recipientEmails.put("TMY","bluefeline310@gmail.com");
		recipientEmails.put("MTP","bluefeline310@gmail.com");
		recipientEmails.put("BRI","bluefeline310@gmail.com");
		recipientEmails.put("CMB","bluefeline310@gmail.com");
		recipientEmails.put("MDR","bluefeline310@gmail.com");
		recipientEmails.put("BNI","bluefeline310@gmail.com");
		recipientEmails.put("KAS","bluefeline310@gmail.com");
		recipientEmails.put("BTN","bluefeline310@gmail.com");
		recipientEmails.put("BIS","bluefeline310@gmail.com");


		try {
			ArrayList<LogsModel> list = generateTextService.returnLogsModel(pathData);
			HashSet<String> uniqSet = generateTextService.uniqueBankCodes(list);
			HashMap<String,String> hashMap = generateTextService.generateMessage(uniqSet, list);
//			hashMap.forEach((key, value) -> System.out.println("\nKey: \n" + key + "\nValue: \n" + value + "\n"));
			hashMap.forEach((key, value) -> {
				String subjectEmail = "Reminder Sign On Envi (" + key + ")";
				String bodyEmail = value;
				String recipientEmail = recipientEmails.get(key);
				senderService.sendEmail(recipientEmail,subjectEmail,bodyEmail);
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
