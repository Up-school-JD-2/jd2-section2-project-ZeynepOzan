package application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Telephone {

	private String brand;
	private String model;
	private final String serialNo;
	private double storage;
	private String system;
	private double availableSpace;

	public Set<Person> contacts;
	public Set<Application> applications;

	public Telephone(String brand, String model, double storage, String system) {
		this.brand = brand;
		this.model = model;
		this.serialNo = generateSeriNumber.apply(brand);
		this.storage = storage;
		this.system = system;
		availableSpace = storage;
		contacts = new LinkedHashSet<>();
		applications = new LinkedHashSet<>();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getStorage() {
		return storage;
	}

	public void setStorage(double storage) {
		this.storage = storage;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public double getAvailableSpace() {
		return availableSpace;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brand, model, storage, system);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (this == null || getClass() != obj.getClass())
			return false;
		Telephone tel = (Telephone) obj;
		return Objects.equals(brand, tel.brand) && Objects.equals(model, tel.model)
				&& Double.compare(storage, tel.storage) == 0 && Objects.equals(system, tel.system);
	}

	@Override
	public String toString() {
		return "Telephone [Serial Number= " + serialNo + " Brand= " + brand + ", Model= " + model + ", Storage= "
				+ storage + " GB" + ", OperatingSystem= " + system + ", Available Space=  " + availableSpace + " GB"
				+ "]";
	}

	// Generate Serial Number
	private final static Function<String, String> generateSeriNumber = b -> {
		Random r = new Random();
		int value = r.hashCode();
		return b + "-" + new SimpleDateFormat("ddyy").format(new Date()) + "-" + value;
	};

	// Add Contanct
	public void addContact(Person person) {
		Function<Boolean, String> resultFunction = per -> per == true ? "Kişi eklendi" : "Kişi zaten kayıtlı";
		boolean addPerson = contacts.add(person);
		System.out.println(resultFunction.apply(addPerson));
	}

	// Delete Contact
	public void deleteContact(String telNo) {
		Function<Boolean, String> resultFunction = tel -> tel == true ? telNo + " numaralı kişi silindi"
				: telNo + " numaralı kişi bulunamadı.";
		boolean deleteTel = contacts.removeIf(t -> t.getTelNo().equals(telNo));
		System.out.println(resultFunction.apply(deleteTel));
	}

	// Update Person
	public boolean updateContact(String telNo) {
		Optional<Person> findPerson = contacts.stream().filter(per -> per.getTelNo().equals(telNo)).findAny();
		if (findPerson.isPresent()) {
			Scanner scanner = new Scanner(System.in);
			Person person = findPerson.get();
			System.out.print("İsim : ");
			person.setName(scanner.next());
			System.out.print("Soyisim : ");
			person.setSurname(scanner.next());
			System.out.print("TelNo : ");
			person.setTelNo(scanner.next());
			System.out.print("Email : ");
			person.setEmail(scanner.next());
			System.out.println("Kişi güncellendi");
			return true;
		}
		System.out.println("Güncelleme başarısız");
		return false;
	}

	// Search Contact
	public List<Person> searchPerson(String name) {
		List<Person> searchPerson = contacts.stream()
				.filter(p -> p.getName().equalsIgnoreCase(name) || p.getSurname().equalsIgnoreCase(name))
				.collect(Collectors.toList());

		if (searchPerson.isEmpty()) {
			System.out.println(name + " ile eşlesen bir kişi bulunmamaktadır.");
			return searchPerson;
		}
		searchPerson.forEach(System.out::println);
		return searchPerson;
	}

	// List Contact
	public void listContacts() {
		contacts.forEach(System.out::println);
	}

	// Control telephone storage
	public boolean controlStorage(Application application, Telephone telephone) {
		Function<Application, Boolean> isStorageEnough = app -> app.getSize() < telephone.getAvailableSpace();
		return isStorageEnough.apply(application);
	}

	// Add Application
	public String addApplication(Application application, Telephone telephone) {
		if (controlStorage(application, telephone)) {
			Function<Boolean, String> resultFunction = added -> added ? "Uygulama eklendi" : "İşlem Başarısız";
			boolean added = applications.add(application);
			availableSpace -= application.getSize();
			return resultFunction.apply(added);
		} else {
			return "Yeterli depolama alanı bulunmamaktadır.";
		}
	}

	// Delete Application
	public void deleteApplication(String appName) {
		Function<Boolean, String> resultFunction = name -> name == true ? "Uygulama silindi"
				: "Uygulama bulunmamaktadır.";
		boolean deleteApp = applications.removeIf(app -> app.getName().equalsIgnoreCase(appName));
		System.out.println(resultFunction.apply(deleteApp));
	}

	// Update Application
	public void updateApplication(String appName) {

		Optional<Application> findApplication = applications.stream()
				.filter(app -> app.getName().equalsIgnoreCase(appName)).findAny();
		if (findApplication.isPresent()) {
			Scanner scanner = new Scanner(System.in);
			Application application = findApplication.get();
			System.out.print("Uygulama İsmi : ");
			application.setName(scanner.next());
			System.out.print("Sürüm :");
			application.setVersion(scanner.next());
			System.out.print("Boyut : ");
			application.setSize(scanner.nextDouble());
			System.out.println("Uygulama güncellendi.");
			return;
		}
		System.out.println("Güncelleme başarısız.");
		return;
	}

	// Search Application
	public void searchApplication(String name) {
		List<Application> searchApp = applications.stream().filter(app -> app.getName().equalsIgnoreCase(name))
				.collect(Collectors.toList());
		if (searchApp.isEmpty()) {
			System.out.println(name + " isimli bir uygulama bulunmamaktadır.");
			return;
		}
		searchApp.forEach(System.out::println);
		return;
	}

	// List Application
	public void listApplication() {
		applications.forEach(System.out::println);
	}
}
