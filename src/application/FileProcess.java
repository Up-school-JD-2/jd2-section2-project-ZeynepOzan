package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class FileProcess {

	private BufferedWriter bufferedWriter = null;
	private BufferedReader bufferedReader = null;

	public void writeFile(Telephone telephone) {
		try {
			bufferedWriter = new BufferedWriter(new FileWriter("telephone.txt", true));
			String telephoneData = "------ Telephone Özellikleri ------\n" + "Telephone [" + "Marka : "
					+ telephone.getBrand() + ", Model : " + telephone.getModel() + ", Seri Numarası : "
					+ telephone.getSerialNo() + ", Toplam Depolama Alanı : " + telephone.getStorage() + " GB "
					+ ", Boş Alan : " + telephone.getAvailableSpace() + " GB " + ", İşletim Sistemi : "
					+ telephone.getSystem() + " ]" + "\n";

			String contactsData = "------ Kişiler ------\n\n" + telephone.contacts
					.stream().map(p -> "Contact [İsim: " + p.getName() + ", Soyisim: " + p.getSurname()
							+ ", Telefon Numarası: " + p.getTelNo() + ", Email: " + p.getEmail() + " ]")
					.collect(Collectors.joining("\n"));

			String appData = "------ Uygulamalar ------\n\n"
					+ telephone.applications
							.stream().map(app -> "Application [İsim : " + app.getName() + ", Sürüm : "
									+ app.getVersion() + ", Boyut : " + app.getSize() + " GB " + " ]")
							.collect(Collectors.joining("\n"));

			// Telefon verisini dosyaya yazma
			bufferedWriter.write(telephoneData + "\n\n");
			bufferedWriter.write(contactsData + "\n\n");
			bufferedWriter.write(appData + "\n\n");

		} catch (IOException e) {
			System.out.println("Data dosyaya yazılırken bir hata oluştu.");
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
					System.out.println("Data dosyaya yazıldı.");
				} catch (IOException e) {
					System.out.println("Dosya kapatılırken bir hata oluştu.");
				}
			}
		}
	}

	public void readFile() {
		try {
			bufferedReader = new BufferedReader(new FileReader("Telephone.txt"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Dosya bulunamadı.");
		} catch (IOException e) {
			System.out.println("Dosya okunurken bir hata oluştu");
		} finally {
			try {
				bufferedReader.close();
				System.out.println("Datalar dosyadan alındı.");
			} catch (IOException e) {
				System.out.println("Dosya kapatılırken bir hata oluştu.");
			}
		}
	}
}
