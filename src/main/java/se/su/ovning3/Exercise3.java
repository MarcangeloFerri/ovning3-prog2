package se.su.ovning3;

import java.io.*;
import java.util.*;

public class Exercise3 {

	private final List<Recording> recordings = new ArrayList<>();

	public void exportRecordings(String fileName) {
		try{
			FileWriter file = new FileWriter(fileName);
			PrintWriter out = new PrintWriter(file);

			for(Recording rec : recordings){
				out.println("<recording>");
				out.println("<artist>" + rec.getArtist()+ "</artist>");
				out.println("<title>" + rec.getTitle()+ "</title>");
				out.println("<year>" + rec.getYear()+ "</year>");
				out.println("<genres>");
				for(String genre : rec.getGenre()){
					out.println("<genre>" + genre + "</genre>" );
				}
				out.println("</genres>");
				out.println("</recording>");
			}
			out.close();
			file.close();

		} catch (FileNotFoundException e) {
			System.err.println("Cant exept "+ fileName);
		}catch (IOException e ){
			System.err.println("Error while reading "+fileName);
		}
	}

	public void importRecordings(String fileName) {
		// läser filens inehåll och omvandlar det till ett recording object
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader in = new BufferedReader(file);

			String line = in.readLine();
			int noOfRecords = Integer.parseInt(line);

			for(int i = 0; i< noOfRecords; i++){
				line = in.readLine();
				String[] tokens = line.split(";");
				int year = Integer.parseInt(tokens[2]);
				line = in.readLine();
				Set<String> genres = new HashSet<>();
				int numOfGenres = Integer.parseInt(line);
				for(int j = 0; j < numOfGenres; j++){
					line = in.readLine();
					genres.add(line);
				}
				Recording recording = new Recording(tokens[1],tokens[0], year, genres);
				recordings.add(recording);
			}
			in.close();
			file.close();

		} catch (FileNotFoundException e) {
			System.err.println("Cant exept "+ fileName);
		}catch (IOException e ){
			System.err.println("Error while reading "+fileName);
		}catch (NumberFormatException e){
			System.err.println("It was not a hole number in the string");
		}

	}

	public Map<Integer, Double> importSales(String fileName) {
		Map<Integer, Double> sales = new HashMap<>();
		try {
			FileInputStream file = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(file);

			int noOfRecords = in.readInt();

			for( int i = 0; i < noOfRecords; i++){
				int year = in.readInt();
				int month = in.readInt();
				in.skipBytes(Integer.BYTES);
				double value = in.readDouble();
				int key = year * 100 + month;
				if(sales.containsKey(key)){
					value += sales.get(key);
				}
				sales.put(key, value);
			}
			in.close();
			file.close();

		} catch (FileNotFoundException e) {
			System.err.println("Cant exept " + fileName);
		} catch (IOException e) {
			System.err.println("Error while reading " + fileName);
		}finally {
			return sales;
		}

	}

	public List<Recording> getRecordings() {
		return Collections.unmodifiableList(recordings);
	}

	public void setRecordings(List<Recording> recordings) {
		this.recordings.clear();
		this.recordings.addAll(recordings);
	}
}

