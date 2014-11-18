package com.Project.AI;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import com.Project.AI.temp;
public class FileClass {
	
	HashMap<Character, ArrayList<temp>> hamMap = new HashMap<Character,ArrayList<temp>>();
	HashMap<Character, ArrayList<temp>> spamMap = new HashMap<Character,ArrayList<temp>>();
	ArrayList<temp> arrylist  =new ArrayList<temp>();
	ArrayList<temp> arrylist1  =new ArrayList<temp>();
	List<sorting> sortinglist  = new ArrayList<sorting>();
	 // static String[] specialCharacters = { ",", "#", ";", "\"", "\'", };  
	   // static String empty = ""; 
	    static void store(String fname, String data) {  
	        try {  
	            FileOutputStream fos = new FileOutputStream(new File(fname));  
	            fos.write(data.getBytes());  
	            fos.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  	
	/*------------------------------------------------------------------------------------------------------------*/
	public void readFile(File Path,boolean hamorSpam) throws IOException{
		
		FileInputStream in = new FileInputStream(Path);
		InputStreamReader inp = new InputStreamReader(in);
		BufferedReader buffer = new BufferedReader(inp);
		String line;
		while ((line = buffer.readLine()) != null) {
			String temp = line.trim();
			if (temp.length() > 0) {
				temp = temp.replaceAll("\\W", " ");
				StringTokenizer st = new StringTokenizer(temp," ß¢Ó£ ß£Å¥Ç§Ô­://.ï¿½01234?:,56!|789}{$%^#ÃÂ«©â€œÂ§_~ -&)(¤=\\\"'][*!+/><@:;.`Ã¥");
		        while(st.hasMoreTokens()){
		        	String str = st.nextToken();
		        	str = str.trim();
		        	//System.out.println(template.word);
		        	temp template = new temp();
		        	//System.out.println(str);
		        	template.word = str;
		        	template.count = 1;
		        	template.FileLocation = Path.getAbsolutePath();
		        	if(hamorSpam)
		        	arrylist.add(template);
		        	else
		        		arrylist1.add(template);
		         }
				}//end of tokens
				}
			}
		
	
	/*-------------------------------------------------------------------------------------------------------------------------------------------*/
	public void printTheFile(HashMap<String,Float> spamMap,HashMap<String,Float> hamMap,PrintWriter out) throws IOException{
			Iterator spamIte= spamMap.keySet().iterator();
			int lineNum = 1 ;
			
			while(spamIte.hasNext()){
				String word = (String) spamIte.next();
				float spamCount =	spamMap.get(word);
				float hamCount = 	hamMap.get(word);
				double spamProb = countProbability(spamCount,countwords(true),countFreq(true,spamMap, hamMap));
				double hamProb = countProbability(hamCount,countwords(false),countFreq(false,spamMap, hamMap));
				sorting s1 = new sorting();
				s1.hamCount = hamCount;
				s1.hamProb = hamProb;
				s1.spamCount = spamCount;
				s1.spamProb = spamProb;
				s1.word = word;
				//System.out.println(word);
				sortinglist.add(s1);
				
				//String temp = lineNum+"   "+word +"   "+hamCount+"   "+hamProb+"   "+spamCount+"   "+spamProb ;
				//out.println(temp);
				lineNum ++;
			}
			
			out.close();
			
			PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter("model.txt")));			
			Collections.sort(sortinglist);
			Iterator<sorting> str = sortinglist.iterator();
			sorting sort ;	
			int lineNum1=1;
			while(str.hasNext()){
				sort = str.next();
				System.out.println(sort.word+ " " +lineNum1);
				String temp1 = lineNum1+"   "+sort.word +"   "+sort.hamCount+"   "+sort.hamProb+"   "+sort.spamCount+"   "+sort.spamProb ;
				pr.println(temp1);
				lineNum1++;			
			}
			pr.close();
	}
	/*-------------------------------------------------------------------------------------------------------------------------------------------*/
	// probability = count of word / ( count of all words + 0.5*total tokens )
	public double countProbability(float count, int totalCount,float countFreq){
			return count/(countFreq + totalCount*0.5) ;
	}
	public float countFreq(boolean hamorSpam,HashMap<String,Float> spamMap,HashMap<String,Float> hamMap){
		HashMap tempMap ;
		float totalCount = 0 ;
		if(hamorSpam){
			tempMap = spamMap ;
		}else{
			tempMap = hamMap;
		}
		
		Iterator tempMapIte = tempMap.entrySet().iterator();
		while(tempMapIte.hasNext()){
			Map.Entry entry = (Entry) tempMapIte.next();
			totalCount = totalCount +(float) entry.getValue();
			
		}
		
		return totalCount ;
		
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------------------------*/
	public int countwords(boolean hamorSpam){
		HashMap tempMap ;
		int totalCount = 0 ;
		if(hamorSpam){
			tempMap = spamMap ;
		}else{
			tempMap = hamMap;
		}
		
		Iterator tempMapIte = tempMap.entrySet().iterator();
		while(tempMapIte.hasNext()){
			Map.Entry entry = (Entry) tempMapIte.next();
			ArrayList<temp> temps =(ArrayList<temp>) entry.getValue();
			totalCount = totalCount + temps.size();
		}
		
		return totalCount ;
		
	}
	/*-------------------------------------------------------------------------------------------------------------------------------------------*/
	public void comparing(){
		compareAdd(spamMap,hamMap);
		
	}
	/*-------------------------------------------------------------------------------------------------------------------------------------------*/
	public void compareAdd(HashMap<Character, ArrayList<temp>> spam,HashMap<Character, ArrayList<temp>> ham){
			HashMap<String,Float> spamwordHash = new HashMap();
			HashMap<String,Float> hamwordHash =new HashMap();
			
			
			Iterator spamIte = spam.entrySet().iterator();
			Iterator hamIte= ham.entrySet().iterator();
			
			
			while(spamIte.hasNext()){
				Map.Entry entry = (Entry) spamIte.next();
				ArrayList<temp> spamWords = (ArrayList<temp>) entry.getValue();
				for(temp Temp : spamWords){
					spamwordHash.put(Temp.word, Temp.count);
				}
			}
			
			while(hamIte.hasNext()){
				Map.Entry entry = (Entry) hamIte.next();
				ArrayList<temp> hamWords = (ArrayList<temp>) entry.getValue();
				for(temp Temp : hamWords){
					hamwordHash.put(Temp.word, Temp.count);
				}
				
			}
			
			Iterator spamwordHashIte = spamwordHash.keySet().iterator();
			Iterator hamwordHashIte = hamwordHash.keySet().iterator();
			
			while(spamwordHashIte.hasNext()){
				String word = (String) spamwordHashIte.next();
				if(hamwordHash.get(word) == null){
					ArrayList objectTemp = ham.get(Character.toLowerCase(word.charAt(0)));
					temp Temp = new temp();
					Temp.word = word ;
					Temp.count= (float) 0.5 ;
					objectTemp.add(Temp);
					hamwordHash.put(word, (float)0.5);
				}
				
				
			}
			
			anotherMethod(spamwordHash,hamwordHash,spam);
			
			/*
			
			while(hamwordHashIte.hasNext()){
				String word = (String) hamwordHashIte.next();
				if(spamwordHash.get(word) == null){
					ArrayList objectTemp = spam.get(Character.toLowerCase(word.charAt(0)));
					temp Temp = new temp();
					Temp.word = word ;
					Temp.count= (float) 0.5 ;
					objectTemp.add(Temp);
					//System.out.println(Temp.count);
					spamwordHash.put(word,(float)0.5);
				}
				
			}
			*/
			
			try {
				printTheFile(spamwordHash,hamwordHash,new PrintWriter(new BufferedWriter(new FileWriter("model.txt"))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
	}
	
	
	public void anotherMethod(HashMap spamwordHash, HashMap hamwordHash,HashMap<Character, ArrayList<temp>> spam){
		
		Iterator hamwordHashIte = hamwordHash.keySet().iterator();
		
		while(hamwordHashIte.hasNext()){
			String word = (String) hamwordHashIte.next();
			if(spamwordHash.get(word) == null){
				ArrayList objectTemp = spam.get(Character.toLowerCase(word.charAt(0)));
				temp Temp = new temp();
				Temp.word = word ;
				Temp.count= (float) 0.5 ;
				objectTemp.add(Temp);
				//System.out.println(Temp.count);
				spamwordHash.put(word,(float)0.5);
			}
			
		}
		
		
	}
	/*-------------------------------------------------------------------------------------------------------------------------------------------*/
	public void writeFile(boolean hamorSpam) throws IOException{
		HashMap tempMap ;
		String stopwords = "a about admin above across ï¿½ after afterwards again against all almost alone along already also although Ã¡" +  
	            "always am among amongst amoungst amount an and another any anyhow anyone anything anyway anywhere" + 
	            "are around as at back be became because become becomes becoming been before beforehand behind being" +
	            "below beside besides between beyond bill both bottom but by call can cannot cant co con could couldnt" +
	            "cry de describe detail do done down due during each eg eight either eleven else elsewhere empty enough" + 
	            "even ever every everyone everything everywhere except few fifteen fify fill find fire first five for former formerly" +
	            "forty found four from front full further get give go had has hasnt have he hence her here hereafter hereby herein hereupon" +
	            "hers herself him himself his how however hundred ie if in inc indeed interest into is it its itself keep last latter latterly least" +
	            "less ltd made many may me meanwhile might mill mine more moreover most mostly move much must my myself name namely neither never" + 
	            "nevertheless next nine no nobody none noone nor not nothing now nowhere of off often on once one only onto or other others" + 
	            "otherwise our ours ourselves out over own part per perhaps please put rather re same see seem seemed seeming seem serious" + 
	            "several she should show side since sincere six sixty so some somehow someone something sometime sometimes somewhere still" + 
	            "such system take ten than that the their them themselves then thence there thereafter thereby therefore therein thereupon" + 
	            "these they thickv thin third this those though three through throughout thru thus to together too top toward towards twelve twenty" + 
	            "two under until up upon us very via was we well were what whatever when whence whenever where whereafter whereas whereby wherein" + 
	            "whereupon wherever whether which while whither who whoever whole Ã… Ã� whom whose why will with within without would yet you" + 
	            "our yours yourself yourselves the";
	
		
		if(hamorSpam){
			tempMap= spamMap;
		}else{
			tempMap =hamMap;
		}
		
		
		String[] str = stopwords.split(" ");
		
		Iterator<temp> it ;
		if(hamorSpam)
		 it= arrylist.iterator();
		else
			it = arrylist1.iterator();
			
		
		while(it.hasNext()){
			temp temp1=it.next();
		    String strElement= temp1.word;
		    float counter = temp1.count;
		    
		    if(strElement.length()<=3){}
		    else{
		    				if(stopwords.contains(strElement)){}
		    				else{
		    					
		    					char element = Character.toLowerCase(strElement.charAt(0)) ;
		    					if(!tempMap.containsKey(element)){
		    						temp tmp = new temp();
		    						tmp.word = temp1.word;
		    						tmp.count = temp1.count;
		    						tmp.FileLocation = temp1.FileLocation;
		    						ArrayList<temp> templa = new ArrayList<temp>();
		    						templa.add(tmp);
		    						tempMap.put(element, templa);
		    					}else{
		    						boolean check = false;
		    						
		    						ArrayList<temp> temps = (ArrayList<temp>) tempMap.get(element);
		    							if(temps.contains(temp1)){
		    								//float Newcounter  = temp1.count +1 ;
		    								Iterator<temp> kau = temps.iterator();
		    								temp template  =null;
		    								int i = 0;
		    								while(kau.hasNext()){
		    									template= kau.next();
		    									i++;
		    									if(template.word.equalsIgnoreCase(temp1.word)){
		    										break;
		    									}
		    									
		    									
		    								}
		    								template.count ++;
				    						check = true;
				    						
		    							}else{
		    								temps.add(temp1);
		    							}
		    						
		    					}
		    				}
		    }
		}
		
		
		Iterator addpointIte = tempMap.entrySet().iterator();
		while(addpointIte.hasNext()){
			Map.Entry mapEntry= (Entry) addpointIte.next();
			ArrayList<temp> words = (ArrayList<temp>) mapEntry.getValue();
			for(temp Temp: words){
				System.out.println(Temp.count+" "+Temp.word+ " ");
				Temp.count= (float) (Temp.count + 0.5) ;
				float z= (float) (Temp.count) ;
				System.out.println(Temp.count+" "+Temp.word+ " " +z);
			}
		}
		
		if(hamorSpam){
			spamMap= tempMap ;
		}else{
			hamMap =tempMap;
		}
	
		display(hamorSpam);
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------------------------*/
	public void display(boolean hamorSpam) throws IOException{
		FileOutputStream out;
		if(hamorSpam)
		 out = new FileOutputStream(new File("src/spamtext.txt"));
		else
			 out = new FileOutputStream(new File("src/hamtext.txt"));
		OutputStreamWriter writer = new OutputStreamWriter(out,"UTF8");
		BufferedWriter bs = new BufferedWriter(writer);
		Iterator entries;
		if(hamorSpam){
			entries = spamMap.entrySet().iterator();
		}else{
			entries = hamMap.entrySet().iterator();
			
		}
		
		while (entries.hasNext()) {
		  Entry thisEntry = (Entry) entries.next();
		  Object key = thisEntry.getKey();
		  ArrayList<temp> value = (ArrayList<temp>) thisEntry.getValue();
		 // System.out.format("Key %s : Values : %s", key,value);
		 // System.out.println();
		  bs.append(" Key : "+ key+"  Values : "+value);
		  bs.newLine();
		}
		bs.write("-----------------------------------------------------------------");
		bs.flush();
		bs.close();
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------------------------*/
	public static void main(String[] args) throws IOException {
		FileClass f2Class = new FileClass();
		String Path1 = "src/mails/20030228_easy_ham_2/easy_ham_2";
		String Path2 = "src/mails/20030228_spam_2/spam_2";
		    
			File folder1 = new File(Path2);
			File[] listOfFiles1 = folder1.listFiles();
			
			    for (int i = 0; i < listOfFiles1.length; i++) {
			    
			      if (listOfFiles1[i].isFile()) {
			    	  	f2Class.readFile(listOfFiles1[i],true);
			    	  	System.out.println(listOfFiles1[i]);
			      	} else if (listOfFiles1[i].isDirectory()) {
			      }
			    }
			    f2Class.writeFile(true);

			File folder2 = new File(Path1);
			File[] listOfFiles2 = folder2.listFiles();
			    for (int i = 0; i < listOfFiles2.length; i++) {
				      if (listOfFiles2[i].isFile()) {
				        f2Class.readFile(listOfFiles2[i],false);
				        System.out.println(listOfFiles2[i]);
				      } else if (listOfFiles2[i].isDirectory()) {
			      }
			    }
				    f2Class.writeFile(false);
				    f2Class.comparing(); 
		}

}
