package us.idinfor.tsclassifier.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Classification {
	private long id;
	
	
	public Classification(){
		
	}

	public Classification(long id) {
		this.id = id;
	}
	
	@JsonProperty
	public long getId(){
		return id;
	}


}
