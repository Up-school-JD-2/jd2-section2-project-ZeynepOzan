package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {
	private String name;
	private String surname;
	private String telNo;
	private String email;

	public List<Person> persons = new ArrayList<>();

	public Person(String name, String surname, String telNo, String email) {
		this.name = name;
		this.surname = surname;
		this.telNo = telNo;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", surname=" + surname + ", telNo=" + telNo + ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, surname, telNo, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (this == null || getClass() != obj.getClass())
			return false;
		Person person = (Person) obj;
		return Objects.equals(name, person.name) && Objects.equals(surname, person.surname)
				&& Objects.equals(telNo, person.telNo) && Objects.equals(email, person.email);
	}

}
