package com.laskutus.free.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileOperations {

	public FileOperations() {

	}

	public void CreateFile(String path) {
		try {
			File myObj = new File(path);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public void WriteToFile(String path, String contents) {
		try {
			FileWriter myWriter = new FileWriter(path);
			myWriter.write(contents);
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public String ReadFile(String path) {
		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			// the json is written as a single line
			String data = myReader.nextLine();
			//System.out.println(data);
			myReader.close();
			return data;
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		// so we can if(fo.ReadFile("rates.json")), if the string is empty then we work
		// with that fact and return an error / 500

		return "";
	}
}
