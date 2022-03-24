package com.example.restservice;

import java.util.*;
import java.util.function.Supplier;
import com.jcraft.jsch.*;

import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@GetMapping("/upload")
	public String upload(@RequestParam(value = "User", defaultValue = "World") String User,
			@RequestParam(value = "EndPoint", defaultValue = "") String EndPoint) {
		String x = "";
		try {
			String user = User;
			String pass = "";
			int SFTPPORT = 22;
			String host = EndPoint;
			String pk = "C:/Users/hp/.ssh/known_hosts";

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");

			JSch jSch = new JSch();
			jSch.setKnownHosts(pk);
			jSch.addIdentity("C:/Users/hp/.ssh/private_key", "empty");
			Session session = jSch.getSession(user, host, SFTPPORT);
			// session.setPassword(pass);
			session.setConfig(config);
			session.connect(1000);
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");

			channelSftp.connect();

			// _________UPLOAD A FILE TO THE SERVER______________________
			channelSftp.put("C:\\Users\\hp\\Desktop\\STC_SEMINAR_REPORT.pdf", "/");
			System.out.println("Uploaded");
			x = x + "Uploaded";
			// _________RENAME A FILE OF THE SERVER______________________
			// channelSftp.rename("home/fileViaIntellij.txt","home/Intellij.txt");

			// _________DOWNLOAD A FILE OF THE SERVER______________________
			// channelSftp.get("home/Intellij.txt","newFile.txt");

			System.out.println("Session connected: " + session.isConnected());
			channelSftp.disconnect();
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return User + " " + EndPoint + " " + x;
	}

	@GetMapping("/list")
	public List<String> list(@RequestParam(value = "User", defaultValue = "World") String User,
			@RequestParam(value = "EndPoint", defaultValue = "") String EndPoint,
			@RequestParam(value = "PEM", defaultValue = "") String PEM) {

		List<String> files = new ArrayList<String>();
		try {
			String user = User;
			String pass = "";
			int SFTPPORT = 22;
			String host = EndPoint;
			String pk = PEM;

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");

			JSch jSch = new JSch();
			jSch.setKnownHosts(pk);
			jSch.addIdentity("C:/Users/hp/.ssh/private_key", "empty");
			Session session = jSch.getSession(user, host, SFTPPORT);
			// session.setPassword(pass);
			session.setConfig(config);
			session.connect(1000);
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");

			channelSftp.connect();
			Vector fileList = channelSftp.ls("/");
			System.out.println("File/s inside home is/are...");
			for (int i = 0; i < fileList.size(); i++) {
				ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) fileList.get(i);
				// if (lsEntry.getFilename().substring(lsEntry.getFilename().length() -
				// 4).equals(".pdf")) {
				files.add(lsEntry.getFilename());
				// }

			}
			System.out.println("Session connected: " + session.isConnected());
			channelSftp.disconnect();
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return files;
	}

	@GetMapping("/download")
	public String download(@RequestParam(value = "User", defaultValue = "World") String User,
			@RequestParam(value = "EndPoint", defaultValue = "") String EndPoint) {
		String x = "";
		try {
			String user = User;
			String pass = "";
			int SFTPPORT = 22;
			String host = EndPoint;
			String pk = "C:/Users/hp/.ssh/known_hosts";

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");

			JSch jSch = new JSch();
			jSch.setKnownHosts(pk);
			jSch.addIdentity("C:/Users/hp/.ssh/private_key", "empty");
			Session session = jSch.getSession(user, host, SFTPPORT);
			// session.setPassword(pass);
			session.setConfig(config);
			session.connect(1000);
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");

			channelSftp.connect();
			channelSftp.get("STC_SEMINAR_REPORT.pdf", "newFile.pdf");
			x = x + "downloaded";
			System.out.println("Session connected: " + session.isConnected());
			channelSftp.disconnect();
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return User + " " + EndPoint + " " + x;
	}

}
