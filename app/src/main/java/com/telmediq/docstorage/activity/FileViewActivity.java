package com.telmediq.docstorage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.telmediq.docstorage.R;
import com.telmediq.docstorage.TelmediqActivity;
import com.telmediq.docstorage.fragment.BottomSheetFileDetailsFragment;
import com.telmediq.docstorage.helper.Constants;
import com.telmediq.docstorage.model.File;

import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileViewActivity extends TelmediqActivity {
	@BindView(R.id.fileViewActivity_fileView)
	ImageView fileView;
	@BindView(R.id.back_arrow)
	MaterialIconView backArrow;
	@BindView(R.id.fileName)
	TextView fileName;
	@BindView(R.id.file_options)
	MaterialIconView fileOptions;

	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_file_view);
		ButterKnife.bind(this);

		if (!getFile()) {
			Toast.makeText(this, R.string.unable_to_get_file_details, Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		fileName.setText(file.getName());
	}

	private boolean getFile() {
		int fileId = getIntent().getIntExtra(Constants.Extras.FILE_ID, -1);
		if (fileId == -1) {
			return false;
		}

		file = File.getFile(realm, String.valueOf(fileId));

		return file != null;
	}

	//<editor-fold desc="Listeners">
	@OnClick(R.id.file_options)
	void onFileOptionClicked(View view) {
		BottomSheetFileDetailsFragment.newInstance(-1).show(getSupportFragmentManager(), BottomSheetFileDetailsFragment.class.getSimpleName());

	}

	@OnClick(R.id.back_arrow)
	void onBackArrowClicked(View view) {
		finish();
	}
	//</editor-fold>
}