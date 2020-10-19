package com.example.cliqbuzz;

public class UserInformation {
	public String name;
	public String surname;
	public String phoneno;

	public UserInformation(){
	}

	public UserInformation(String name,String surname, String phoneno){
		this.name = name;
		this.surname = surname;
		this.phoneno = phoneno;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getPhoneno() {
		return phoneno;
	}
}
