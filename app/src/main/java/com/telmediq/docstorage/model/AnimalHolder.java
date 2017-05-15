package com.telmediq.docstorage.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sean1 on 5/15/2017.
 */

public class AnimalHolder {
	public static final int HEADER = 0;
	public static final int CAT = 1;
	public static final int DOG = 2;

	private String header;
	private Cat cat;
	private Dog dog;

	//<editor-fold desc="Constructors">
	public AnimalHolder(String header) {
		this.header = header;
	}

	public AnimalHolder(Cat cat) {
		this.cat = cat;
	}

	public AnimalHolder(Dog dog) {
		this.dog = dog;
	}
	//</editor-fold>

	public static List<AnimalHolder> generateAnimalHolder(List<Cat> cats, List<Dog> dogs) {
		List<AnimalHolder> holders = new ArrayList<>();

		if (cats.size() > 0) {
			holders.add(new AnimalHolder("Cats"));
		}
		for (Cat cat : cats) {
			holders.add(new AnimalHolder(cat));
		}

		if (dogs.size() > 0) {
			holders.add(new AnimalHolder("Dogs"));
		}
		for (Dog dog : dogs) {
			holders.add(new AnimalHolder(dog));
		}

		return holders;
	}

	public int getType() {
		if (cat != null) {
			return CAT;
		} else if (dog != null) {
			return DOG;
		} else {
			return HEADER;
		}
	}

	//<editor-fold desc="Getter and Setters">
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Cat getCat() {
		return cat;
	}

	public void setCat(Cat cat) {
		this.cat = cat;
	}

	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}
	//</editor-fold>
}
