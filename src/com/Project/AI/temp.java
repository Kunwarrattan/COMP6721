package com.Project.AI;

public class temp {
	String word;
	float count;
	String FileLocation;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		temp other = (temp) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equalsIgnoreCase(other.word))
			return false;
		return true;
	}
	public String toString(){
		return word+" "+count;
	}
	
}
