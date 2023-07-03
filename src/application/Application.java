package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Application {
	private String name;
	private String version;
	private double size;
	public List<Application> applications = new ArrayList<>();

	public Application(String name, String version, double size) {
		this.name = name;
		this.version = version;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public double getSize() {
		return size;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	public void setSize(double size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "App [name=" + name + ", version=" + version + ", size=" + size + "]";
	}

	@Override
	public int hashCode() {

		return Objects.hash(name, version, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (this == null || getClass() != obj.getClass())
			return false;
		Application app = (Application) obj;

		return Objects.equals(name, app.name) && Objects.equals(version, app.version) && Objects.equals(size, app.size);
	}
}
