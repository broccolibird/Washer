package com.freescale.iastate.washer.progress;

import com.freescale.iastate.washer.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TimerFragment extends Fragment {
	private long millisLeft;
	private long totalMillis;
	private int pct;
	PercentDoneListener callBack;

	public interface PercentDoneListener {
		public void percentDoneUpdated(int percent);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.timerfragment, container, false);

	}
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		callBack = (PercentDoneListener) activity;
	}
	
	public void createCountdown(int minutes) {

		final TextView program_timer = (TextView) getView().findViewById(
				R.id.timer);

		totalMillis = minutesToMillis(minutes);
		

		new CountDownTimer(minutesToMillis(minutes), 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				program_timer.setText(millisToTimeString(millisUntilFinished));
				millisLeft = millisUntilFinished;
				if (pct < percentComplete()){ 
					pct = percentComplete();
					callBack.percentDoneUpdated(pct);
				}
			}

			@Override
			public void onFinish() {
				program_timer.setText("done");
			}
		}.start();
	}

	private String millisToTimeString(long millis) {
		long total_seconds = millis / 1000;

		long minutes = total_seconds / 60;
		long seconds = total_seconds % 60;

		String second_string = (seconds < 10) ? "0" + seconds : "" + seconds;

		return minutes + ":" + second_string;
	}

	private int minutesToMillis(int minutes) {
		return 1000 * (minutes * 60);
	}

	public int percentComplete() {
		return (int) Math
				.round(100 - (((float) millisLeft / (float) totalMillis) * 100.0));
	}
}
