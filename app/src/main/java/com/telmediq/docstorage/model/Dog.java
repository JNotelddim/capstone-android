package com.telmediq.docstorage.model;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sean1 on 5/15/2017.
 */

public class Dog extends RealmObject {
	@PrimaryKey
	private String uuid;
	private String name;
	private Date birthdate;

	//<editor-fold desc="Fetching">
	public static RealmResults<Dog> getDogs(Realm realm) {
		return realm.where(Dog.class).findAllSorted("birthdate");
	}

	public static Dog getDog(Realm realm, String uuid) {
		return realm.where(Dog.class).equalTo("uuid", uuid).findFirst();
	}
	//</editor-fold>

	//<editor-fold desc="Getter and Setters">
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	//</editor-fold>
}
