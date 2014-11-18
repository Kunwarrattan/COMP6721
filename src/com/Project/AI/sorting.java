package com.Project.AI;
/*
* @author kuunwar
*Data structure 
*/
public class sorting implements Comparable<sorting>{
	String word;
	float hamCount;
	double hamProb;
	float spamCount;
	double spamProb ;
	@Override
	public int compareTo(sorting arg0) {
		return word.compareToIgnoreCase(arg0.word);
	}
}
