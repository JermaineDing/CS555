/*
 * Code is updated
 * */
package Project;

import Data.Family;
import Data.Individual;

import java.io.*;
import java.util.*;

public class ProcessData {

	List<Individual> individuals = new ArrayList<Individual>();
	List<Family> families = new ArrayList<Family>();
	UserStories ustor = new UserStories();
	
	// get arguments
	private String getArguments(String[] parseLine) {
		String arguments = "";
		for (int i = 2; i < parseLine.length; i++) {
			arguments = arguments + " " + parseLine[i];
		}
		return arguments.trim();
	}

	// get id
	private String getId(String Id) {
		return Id.replace("@", "");
	}

	// read from a file
	public void readFile(String file) throws IOException {

		FileInputStream fileInput = null;
		BufferedReader bufferRead = null;

		try {

			fileInput = new FileInputStream(file);
			bufferRead = new BufferedReader(new InputStreamReader(fileInput));
			System.out.println();

			String line = null;
			boolean isObject = false;
			Individual ind = null;
			Family fam = null;

			while ((line = bufferRead.readLine()) != null) {
				String[] parseLine = (line.split("\\s+"));              // split the string
				int level = Integer.valueOf(parseLine[0]);              // def for the value
				String tag = parseLine[1];                              // def for tag
				String arguments = (parseLine.length > 2) ? getArguments(parseLine) : null;
				if (level == 0) {
					if ("INDI".equals(arguments)) {
						ind = new Individual();
						if (ind != null) {
							ind.setId(getId(tag));
							individuals.add(ind);
							isObject = true;                            // it's an individual or a family
						}
					} else if ("FAM".equals(arguments)) {
						fam = new Family();
						if (fam != null) {
							families.add(fam);
							fam.setId(getId(tag));
							isObject = true;
						}
					} else {
						isObject = false;
					}
				}
				if (isObject) {
					if ("NAME".equals(tag)) {
						ind.setName(arguments);
					}
					else if ("HUSB".equals(tag)) {
						fam.setHusband(getId(arguments));
					}
					else if ("WIFE".equals(tag)) {
						fam.setWife(getId(arguments));
					}
					else if ("SEX".equals(tag)) {
						ind.setSex(arguments.charAt(0));
					} 
				}
				if(level == 1){
					if("MARR".equals(tag)){
						line = bufferRead.readLine();
						String []nextLine = (line.split("\\s+"));
						if(nextLine[1].equals("DATE")){
							String wedDate = nextLine[2] +" "+ nextLine[3] +" "+ nextLine[4];
							fam.setWeddingDate(wedDate);
						}
					}
					else if("DIV".equals(tag)){
						line = bufferRead.readLine();
						String []nextLine = (line.split("\\s+"));
						if(nextLine[1].equals("DATE")){
							String divDate = nextLine[2] +" "+ nextLine[3] +" "+ nextLine[4];
							fam.setDivorceDate(divDate);
						}
					}
					else if("BIRT".equals(tag)){
						line = bufferRead.readLine();
						String []nextLine = (line.split("\\s+"));
						if(nextLine[1].equals("DATE")){
							String birDate = nextLine[2] +" "+ nextLine[3] +" "+ nextLine[4];
							ind.setBirthDate(birDate);
						}
					}
					else if("DEAT".equals(tag)){
						line = bufferRead.readLine();
						String []nextLine = (line.split("\\s+"));
						if(nextLine[1].equals("DATE")){
							String deathDate = nextLine[2] +" "+ nextLine[3] +" "+ nextLine[4];
							ind.setDeathDate(deathDate);
						}
					}
				}
			}
		} catch (FileNotFoundException ex) {
			System.out.println("File is not found!");
		}
	}

	// get the info for individuals
	public String getIndividual(String id) {
		if (individuals != null && !individuals.isEmpty()) {															
			for (int i = 0; i < individuals.size(); i++) {
				Individual indiO = individuals.get(i);
				if (indiO.getId().equals(id)) { 
					return indiO.getName(); 
				}
			}
		}
		return "No Individual Error!";
	}

	// print individual info
	public Vector<String> print_individual() {
		Vector<String> s = new Vector<String>();
		if (individuals == null || individuals.isEmpty()) {
			s.add("No individuals!");
		} else {
			
			for (int i = 0; i < individuals.size(); i++) {
				Individual indiO1 = individuals.get(i);
				//sprint 2
				
				String res1 = ustor.Spr2_D_B(indiO1);
				String res2 = ustor.Spr2_D_D(indiO1);
				String res3 = ustor.Spr2_D_US3(indiO1);
				if(!res1.equals("")){
					s.add("Check Birth Date Before Current:");
					s.add(res1);
				}
				if(!res2.equals("")){
					s.add("Check Death Date Before Current:");
					s.add(res2);
				}
				if(!res3.equals("")){
					s.add("Check Birth Date Before Death:");
					s.add(res3);
				}
				for(int j = 0; j < individuals.size(); j++){
					Individual indiO2 = individuals.get(j);
					if (i != j){
						
						//sprint 1
						String tmp = ustor.Spr1_D(indiO1, indiO2);
						if(!tmp.equals("")) s.add(tmp);
						
						// sprint 3
						
						// sprint 4
					}
				}
				
				s.add("ID: @" + indiO1.getId() + "@");
				s.add("Name: " + indiO1.getName());
				s.add(ustor.Spr1_Zhu(indiO1));
			}
		}
		return s;
	}

	// print family information
	public Vector<String> print_family() {
		Vector<String> s = new Vector<String>();
		if (families == null || families.isEmpty()) {
			System.out.println("No families!");
		} else {
			for (int i = 0; i < families.size(); i++) {
				Family famO1 = families.get(i);
				
				//sprint 2 
				String res1 = ustor.Spr2_D_M(famO1);
				String res2 = ustor.Spr2_D_D(famO1);
				if(!res1.equals("")){
					s.add("Check Marriage Date Before Current:");
					s.add(res1);
				}
				if(!res2.equals("")){
					s.add("Check Divorce Date Before Current:");
					s.add(res2);
				}
				
				//sprint 2  by Zhaohui Zhu
				String res3 = ustor.Spr2_Zhu(famO1);

				
				for(int j = 0; j < families.size(); j++){
					Family famO2 = families.get(j);
					if(i != j){
						
						// sprint 1
						String tmp = ustor.Spr1_D(famO1, famO2);
						if(!tmp.equals(""))		s.add(tmp);
												
						// sprint 3
						
						// sprint 4
					}
				}
				s.add("ID:@" + famO1.getId() + "@");
				s.add("Husband: " + getIndividual(famO1.getHusband()));
				s.add("Wife: " + getIndividual(famO1.getWife()));
				s.add("Wedding Date:" +famO1.getWeddingDate());
			}
		}
		return s;
	}
}