package com.kcj.animationfriend.bean;

import java.util.ArrayList;
import java.util.List;

public class Area {

	private String name;
	private List objectList = new ArrayList();
	
	public Area(){
	}
			
	public Area(String name,List objectList ){
		this.name = name;
		this.objectList = objectList;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getObjectList() {
		return objectList;
	}

	public void setObjectList(List objectList) {
		this.objectList = objectList;
	}

	
}
