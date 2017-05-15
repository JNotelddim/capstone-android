package com.telmediq.docstorage.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telmediq.docstorage.R;
import com.telmediq.docstorage.model.AnimalHolder;
import com.telmediq.docstorage.model.Cat;
import com.telmediq.docstorage.model.Dog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 03/05/17.
 */

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {
	private List<AnimalHolder> data;
	private Listener listener;

	public AnimalAdapter(List<AnimalHolder> data, Listener listener) {
		this.data = data;
		this.listener = listener;
	}

	@Override
	public int getItemViewType(int position) {
		return data.get(position).getType();
	}

	public AnimalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View contentView;
		switch (viewType) {
			case AnimalHolder.CAT:
				contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_cat, parent, false);
				break;
			case AnimalHolder.DOG:
				contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_dog, parent, false);
				break;
			case AnimalHolder.HEADER:
			default:
				contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_header, parent, false);
		}

		return new ViewHolder(contentView);
	}

	@Override
	public void onBindViewHolder(AnimalAdapter.ViewHolder holder, int position) {
		switch (holder.getItemViewType()) {
			case AnimalHolder.CAT:
				holder.bindCat(data.get(position).getCat(), listener);
				break;
			case AnimalHolder.DOG:
				holder.bindDog(data.get(position).getDog(), listener);
				break;
			case AnimalHolder.HEADER:
			default:
				holder.bindHeader(data.get(position).getHeader());
		}
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public void updateData(List<AnimalHolder> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	static class ViewHolder extends RecyclerView.ViewHolder {
		//<editor-fold desc="View Initialization">
		// rootView exists in all views (doesn't need to be nullable)
		@BindView(R.id.listItem_rootView) View rootView;

		// We annotate these views with nullable because they do not exist
		// on all the view types, butterknife would crash if we didn't add this annotation

		//<editor-fold desc="listItem_header">
		@Nullable
		@BindView(R.id.listItem_titleTextView)
		TextView headerTitleText;
		//</editor-fold>

		//<editor-fold desc="listItem_cat">
		@Nullable
		@BindView(R.id.listItemCat_titleTextView)
		TextView catTitleText;
		//</editor-fold>

		//<editor-fold desc="listItem_dog">
		@Nullable
		@BindView(R.id.listItemDog_titleTextView)
		TextView dogTitleText;
		//</editor-fold>
		//</editor-fold>

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}

		void bindHeader(String headerText) {
			if (headerTitleText == null) {
				return;
			}

			headerTitleText.setText(headerText);
		}

		void bindCat(final Cat cat, final AnimalAdapter.Listener listener) {
			if (catTitleText == null) {
				return;
			}

			catTitleText.setText(cat.getName());

			if (listener == null) {
				return;
			}

			rootView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onCatClicked(cat.getUuid());
				}
			});
		}

		void bindDog(final Dog dog, final AnimalAdapter.Listener listener) {
			if (dogTitleText == null) {
				return;
			}

			dogTitleText.setText(dog.getName());

			if (listener == null) {
				return;
			}

			rootView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onDogClicked(dog.getUuid());
				}
			});
		}
	}

	public interface Listener {
		void onCatClicked(String uuid);

		void onDogClicked(String uuid);
	}
}
