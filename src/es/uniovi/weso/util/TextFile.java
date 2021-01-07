package es.uniovi.weso.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public class TextFile {

	private String filename;

	public synchronized void add(String text) {
		try {
			BufferedWriter file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename),"UTF-8"));
			file.write(text);
			file.newLine();
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void write(String text) {
		try {
			BufferedWriter file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename),"UTF-8"));
			file.write(text);
			file.newLine();
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TextFile(String filename) {
		super();
		this.filename = filename;
	}

	public synchronized String read() {
		String out = "";
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				out += strLine + "\n";
			}
			in.close();
		} catch (Exception e) {
//			System.err.println("Error: " + e.getMessage());
			write("");
		}
		return out;
	}

	public synchronized boolean exists(String string){
		boolean success=false;
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null && success==false) {
				if(string.equals(strLine)){
					success=true;
				}
			}
			in.close();
		} catch (Exception e) {
	//		System.err.println("Error: " + e.getMessage());
			write("");
		}
		return success;
	}
	

	public synchronized Boolean existsSubstring(String string){
		boolean success=false;
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null && success==false) {
				if(strLine.contains(string)){
					success=true;
				}
			}
			in.close();
		} catch (Exception e) {
	//		System.err.println("Error: " + e.getMessage());
			write("");
		}
		return success;
	}
	

	public synchronized Long countSubstring(String string){
		Long counter = Long.valueOf(0);
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if(strLine.contains(string)){
					counter++;
				}
			}
			in.close();
		} catch (Exception e) {
		//	System.err.println("Error: " + e.getMessage());
		}
		return counter;
	}
	
	
}
