package application;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TelephoneManagement {

	private final String username = "ozanzzeynep";
	private final String password = "12345";
	private FileProcess fileProcess;

	public Set<Telephone> telephones;

	public TelephoneManagement() {
		fileProcess = new FileProcess();
		telephones = new LinkedHashSet<>();
	}

	// Add Telephone
	public void addTelephone(Telephone telephone) {
		Function<Boolean, String> resultFunction = tel -> tel == true ? "Telefon eklendi" : "Telefon zaten kayıtlı";
		boolean addTelephon = telephones.add(telephone);
		System.out.println(resultFunction.apply(addTelephon));
	}

	// Delete Telephone
	public void deleteTelephone(String serialNumber) {
		Function<Boolean, String> resultFunction = num -> num == true ? serialNumber + " numaralı telefon silindi"
				: serialNumber + " numaralı telefon bulunamadı.";
		boolean deleteTel = telephones.removeIf(tel -> tel.getSerialNo().equalsIgnoreCase(serialNumber));
		System.out.println(resultFunction.apply(deleteTel));
	}

	// Search Telephone
	public List<Telephone> searchTelephone(String brand) {
		List<Telephone> searchTel = telephones.stream().filter(tel -> tel.getBrand().equalsIgnoreCase(brand))
				.collect(Collectors.toList());
		if (searchTel.isEmpty()) {
			System.out.println(brand + " marka telefon bulunmamaktadır.");
			return searchTel;
		}
		searchTel.forEach(System.out::println);
		return searchTel;
	}

	// Update Telephone
	public boolean updateTelephone(String seriNo) {
		Optional<Telephone> findTelephone = telephones.stream().filter(tel -> tel.getSerialNo().equals(seriNo))
				.findAny();
		if (findTelephone.isPresent()) {
			Scanner scanner = new Scanner(System.in);
			Telephone telephone = findTelephone.get();
			System.out.print("Marka : ");
			telephone.setBrand(scanner.next());
			System.out.print("Model : ");
			telephone.setModel(scanner.next());
			System.out.print("Depolama Alanı : ");
			telephone.setStorage(scanner.nextDouble());
			System.out.print("İşletim Sistemi : ");
			telephone.setSystem(scanner.next());
			System.out.println(seriNo + " numaralı telefon güncellendi");
			return true;
		}
		System.out.println("Güncelleme başarısız");
		return false;
	}

	// List telephone
	public void listTelephone() {
		telephones.forEach(System.out::println);
	}

	// Choose Telephone
	public Telephone chooseTelephone(String serialNumber) {
		Optional<Telephone> chooseTel = telephones.stream().filter(tel -> tel.getSerialNo().equals(serialNumber))
				.findAny();
		if (chooseTel.isPresent()) {
			Telephone choseTel = chooseTel.get();
			return choseTel;
		}
		System.out.println("Telefon bulunamadı");
		return null;
	}

	public void userScreen() {
		Scanner scanner = new Scanner(System.in);

		int exit = 0;
		System.out.println("------- Login -------");
		do {
			System.out.print("Username : ");
			String username = scanner.next();
			System.out.print("Password : ");
			String password = scanner.next();
			if (userAccess(username, password)) {
				System.out.println("Login Successfull");
				int mainChoice, telChoice, personChoice, appChoice, fileChoice;
				do {
					mainScreen();
					System.out.print("Seçiminiz: ");
					mainChoice = scanner.nextInt();
					switch (mainChoice) {
					case 1 -> {
						// Tel ekle
						System.out.print("Brand : ");
						String brand = scanner.next();
						System.out.print("Model : ");
						String model = scanner.next();
						System.out.print("Storage : ");
						double storage = scanner.nextDouble();
						System.out.print("Operating System : ");
						String operatingSystem = scanner.next();
						Telephone telephone = new Telephone(brand, model, storage, operatingSystem);
						addTelephone(telephone);
					}
					case 2 -> {
						// Tel sil
						if (telephones.isEmpty()) {
							System.out.println("Kayıtlı telefon bulunmamaktadır.");
							continue;
						}
						System.out.print("Telefon Seri No : ");
						String serialNumber = scanner.next();
						deleteTelephone(serialNumber);
					}
					case 3 -> {
						// Tel ara
						System.out.print("Marka : ");
						String brand = scanner.next();
						searchTelephone(brand);
					}
					case 4 -> {
						listTelephone();
						System.out.print("Güncellemek istediğiniz telefonun seri numarasını giriniz: ");
						String seriNo = scanner.next();
						updateTelephone(seriNo);
					}
					case 5 -> {
						listTelephone();
					}
					case 6 -> {
						listTelephone();
						System.out.print("Telefon Seri No :");
						String seriNum = scanner.next();
						if (chooseTelephone(seriNum) != null) {
							Telephone telephone = chooseTelephone(seriNum);
							do {
								mainTelScreen();
								System.out.print("Seçiminiz : ");
								telChoice = scanner.nextInt();
								switch (telChoice) {
								case 1 -> {
									// Kişi yönetimi
									do {
										managePersonScreen();
										System.out.print("Seçiminiz: ");
										personChoice = scanner.nextInt();
										switch (personChoice) {
										case 1 -> {
											System.out.print("İsim : ");
											String name = scanner.next();
											System.out.print("Soyisim : ");
											String surname = scanner.next();
											System.out.print("Tel No : ");
											String telNo = scanner.next();
											System.out.print("Email : ");
											String email = scanner.next();
											telephone.addContact(new Person(name, surname, telNo, email));
										}
										case 2 -> {
											System.out.print("Tel No giriniz: ");
											String telNo = scanner.next();
											telephone.deleteContact(telNo);
										}
										case 3 -> {
											System.out.print("Kişi ismi giriniz : ");
											String name = scanner.next();
											telephone.searchPerson(name);
										}
										case 4 -> {
											telephone.listContacts();
											System.out.print("Güncellemek istediğiniz kişi TelNo : ");
											String telNo = scanner.next();
											telephone.updateContact(telNo);
										}
										case 5 -> {
											if (telephones.isEmpty()) {
												System.out.println("Kayıtlı telefon bulunmamaktadır.");
												continue;
											}
											telephone.listContacts();
										}
										case 6 -> {
											personChoice = -1;
										}
										}
									} while (personChoice != -1);
								}
								case 2 -> {
									// Uygulama yönetimi
									do {
										manageAppScreen();
										System.out.print("Seçiminiz : ");
										appChoice = scanner.nextInt();
										switch (appChoice) {
										case 1 -> {
											System.out.print("Uygulama Adı: ");
											String name = scanner.next();
											System.out.print("Sürüm : ");
											String version = scanner.next();
											System.out.print("Boyutu : ");
											double size = scanner.nextDouble();
											System.out.println(telephone
													.addApplication(new Application(name, version, size), telephone));
										}
										case 2 -> {
											System.out.print("Silmek istediğiniz uygulamanın adını giriniz : ");
											String appName = scanner.next();
											telephone.deleteApplication(appName);
										}
										case 3 -> {
											System.out.print("Uygulama ismi giriniz: ");
											String appName = scanner.next();
											telephone.searchApplication(appName);
										}
										case 4 -> {
											System.out.print("Güncellemek istediğiniz uygulama adını giriniz : ");
											String appName = scanner.next();
											telephone.updateApplication(appName);
										}
										case 5 -> {
											telephone.listApplication();

										}
										case 6 -> {
											appChoice = -1;
										}
										}
									} while (appChoice != -1);
								}
								case 3 -> {
									// Dosya Yönetimi
									do {
										manageFileScreen();
										System.out.print("Seçiminizi : ");
										fileChoice = scanner.nextInt();

										switch (fileChoice) {
										case 1 -> {
											FileProcess fileWrite = new FileProcess();
											fileWrite.writeFile(telephone);
										}
										case 2 -> {
											FileProcess fileRead = new FileProcess();
											fileRead.readFile();
										}
										case 3 -> {
											fileChoice = -1;
										}
										}
									} while (fileChoice != -1);
								}
								case 4 -> {
									telChoice = -1;
								}
								}
							} while (telChoice != -1);
						}
					}
					case 7 -> {
						exit = -1;
						mainChoice = -1;
						System.out.println("Program Sonlandı");
					}
					}
				} while (mainChoice != -1);
			}
		} while (exit != -1);
	}

	private boolean userAccess(String username, String password) {
		BiPredicate<String, String> userBiPredicate = (name,
				pass) -> (name.equals(this.username) && pass.equals(this.password));
		return userBiPredicate.test(username, password);
	}

	public void mainScreen() {
		List<String> telMenu = List.of("1- Telefon Ekle", "2- Telefon Sil", "3- Telefon Ara", "4- Telefon Güncelle",
				"5- Telefonları Listele", "6- Telefon Seç", "7- Programı Sonlandır");
		telMenu.forEach(System.out::println);
	}

	public void managePersonScreen() {
		List<String> personMenu = List.of("1- Kişi Ekle", "2- Kişi Sil", "3- Kişi ara", "4- Kişi Güncelle",
				"5- Kişileri Listele", "6- Bir önceki menu'ye dön");
		personMenu.forEach(System.out::println);
	}

	public void manageAppScreen() {
		List<String> appMenu = List.of("1- Uygulama Ekle", "2- Uygulama Sil", "3- Uygulama Ara", "4- Uygulama Güncelle",
				"5- Uygulamaları Listele", "6- Bir önceki menu'ye dön");
		appMenu.forEach(System.out::println);
	}

	public void manageFileScreen() {
		List<String> fileMenu = List.of("1- Dosyaya bilgileri yedekle", "2- Dosyadan bilgileri al",
				"3- Bir önceki menu'ye dön");
		fileMenu.forEach(System.out::println);
	}

	public void mainTelScreen() {
		List<String> mainMenu = List.of("1- Kişi Yönetimi", "2- Uygulama Yönetimi", "3- Dosya İşlemleri",
				"4- Bir önceki menu'ye geri dön");
		mainMenu.forEach(System.out::println);
	}
}
