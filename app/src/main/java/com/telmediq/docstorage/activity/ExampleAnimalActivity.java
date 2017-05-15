package com.telmediq.docstorage.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.telmediq.docstorage.R;
import com.telmediq.docstorage.TelmediqActivity;
import com.telmediq.docstorage.adapter.AnimalAdapter;
import com.telmediq.docstorage.model.AnimalHolder;
import com.telmediq.docstorage.model.Cat;
import com.telmediq.docstorage.model.Dog;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ExampleAnimalActivity extends TelmediqActivity {
	//<editor-fold desc="View Initialization">
	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.homeActivity_recyclerView)
	RecyclerView recyclerView;
	//</editor-fold>

	RealmResults<Cat> cats;
	RealmResults<Dog> dogs;

	AnimalAdapter animalAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		getAnimals();
		setupToolbar();
		setupViews();

		Snackbar.make(recyclerView, "Click fab to add animal", Snackbar.LENGTH_LONG).show();
	}

	private void getAnimals() {
		cats = Cat.getCats(realm);
		dogs = Dog.getDogs(realm);

		cats.addChangeListener(animalChangeListener);
		dogs.addChangeListener(animalChangeListener);
	}

	private void setupToolbar() {
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void setupViews() {
		setupRecyclerView();
	}

	private void setupRecyclerView() {
		List<AnimalHolder> animalHolders = AnimalHolder.generateAnimalHolder(cats, dogs);

		if (recyclerView.getAdapter() == null) {
			animalAdapter = new AnimalAdapter(animalHolders, animalListener);
			recyclerView.setLayoutManager(new LinearLayoutManager(this));
			recyclerView.setAdapter(animalAdapter);
		} else {
			animalAdapter.updateData(animalHolders);
		}
	}

	private void createANewRandomAnimalForTesting() {
		realm.executeTransactionAsync(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				String uuid = UUID.randomUUID().toString();

				if (Math.random() < 0.5) {
					Cat cat = new Cat();
					cat.setUuid(uuid);
					cat.setName(String.format("Cat %s", uuid));
					cat.setBirthdate(new Date());

					realm.copyToRealmOrUpdate(cat);
				} else {
					Dog dog = new Dog();
					dog.setUuid(uuid);
					dog.setName(String.format("Dog %s", uuid));
					dog.setBirthdate(new Date());

					realm.copyToRealmOrUpdate(dog);
				}
			}
		});
	}

	//<editor-fold desc="Listeners">
	RealmChangeListener animalChangeListener = new RealmChangeListener() {
		@Override
		public void onChange(Object element) {
			setupRecyclerView();
		}
	};

	AnimalAdapter.Listener animalListener = new AnimalAdapter.Listener() {
		@Override
		public void onCatClicked(final String uuid) {
			realm.executeTransactionAsync(new Realm.Transaction() {
				@Override
				public void execute(Realm realm) {
					Cat cat = Cat.getCat(realm, uuid);
					if (cat == null || !cat.isValid()) {
						return;
					}

					cat.deleteFromRealm();
				}
			}, new Realm.Transaction.OnSuccess() {
				@Override
				public void onSuccess() {
					Toast.makeText(ExampleAnimalActivity.this, "Meow", Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public void onDogClicked(final String uuid) {
			realm.executeTransactionAsync(new Realm.Transaction() {
				@Override
				public void execute(Realm realm) {
					Dog dog = Dog.getDog(realm, uuid);
					if (dog == null || !dog.isValid()) {
						return;
					}

					dog.deleteFromRealm();
				}
			}, new Realm.Transaction.OnSuccess() {
				@Override
				public void onSuccess() {
					Toast.makeText(ExampleAnimalActivity.this, "Bark", Toast.LENGTH_SHORT).show();
				}
			});
		}
	};

	@OnClick(R.id.fab)
	public void onFabClicked() {
		createANewRandomAnimalForTesting();
	}
	//</editor-fold>
}