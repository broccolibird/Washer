package com.freescale.iastate.washer;

import java.text.DateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.freescale.iastate.washer.util.*;
import com.freescale.iastate.washer.weather.JSONParser;
import com.freescale.iastate.washer.weather.WeatherFragment;
import com.freescale.iastate.washer.weather.WeatherObject;

public class WeatherActivity extends Activity implements MenuInterface,
		DisplayInterface {
	final char degree = 0x00B0;
	WeatherObject[] dayArray = new WeatherObject[12];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		rootIntent.setHelpText("Weather Forecast", getText(R.string.weather_help));
		
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.weather);

			ForecastWeatherTask task = new ForecastWeatherTask();
			task.execute();
		} else {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.weathernotconnected);
			Button wifiButton = (Button) findViewById(R.id.wifiButton);
			wifiButton.setOnClickListener(wifiListener);

		}

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

	}

	private class ForecastWeatherTask extends AsyncTask<Void, Void, String> {

		protected String doInBackground(Void... params) {
			try {
				String zip = "50014";
				TextView weatherZip = (TextView) findViewById(R.id.weatherZip);
				weatherZip.setText("Weather for: " + zip + "    Last updated: "
						+ DateFormat.getDateTimeInstance().format(new Date()));
				JSONParser parser = new JSONParser();
				JSONParser.zip = zip;
				JSONObject json = parser.getJSONFromUrl(JSONParser.threedayURL);

				JSONObject forecast = json.getJSONObject("forecast");
				JSONObject simpleForecast = forecast
						.getJSONObject("simpleforecast");
				JSONArray simpleForecastDay = null;
				simpleForecastDay = simpleForecast.getJSONArray("forecastday");
				JSONObject forecastText = forecast
						.getJSONObject("txt_forecast");
				JSONArray textForecastDay = null;
				textForecastDay = forecastText.getJSONArray("forecastday");

				for (int i = 0; i < 10; i++) {
					JSONObject day = simpleForecastDay.getJSONObject(i);
					JSONObject date = day.getJSONObject("date");
					JSONObject high = day.getJSONObject("high");
					JSONObject low = day.getJSONObject("low");
					JSONObject fctday = textForecastDay.getJSONObject(i);

					dayArray[i] = new WeatherObject();
					dayArray[i].weekday = date.getString("weekday");
					dayArray[i].high = high.getString("fahrenheit");
					dayArray[i].low = low.getString("fahrenheit");
					dayArray[i].conditions = day.getString("conditions");
					dayArray[i].image = weatherImage(day
							.getString("conditions"));
					dayArray[i].forecast = fctday.getString("fcttext");

				}

				return null;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		protected void onPostExecute(String result) {

			WeatherFragment day1 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day1fragment);
			day1.updateWeather(dayArray[0]);

			WeatherFragment frag2 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day2fragment);
			frag2.updateWeather(dayArray[1]);

			WeatherFragment frag3 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day3fragment);
			frag3.updateWeather(dayArray[2]);

			WeatherFragment frag4 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day4fragment);
			frag4.updateWeather(dayArray[3]);

			WeatherFragment frag5 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day5fragment);
			frag5.updateWeather(dayArray[4]);

			WeatherFragment frag6 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day6fragment);
			frag6.updateWeather(dayArray[5]);

			WeatherFragment frag7 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day7fragment);
			frag7.updateWeather(dayArray[6]);

			WeatherFragment frag8 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day8fragment);
			frag8.updateWeather(dayArray[7]);

			WeatherFragment frag9 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day9fragment);
			frag9.updateWeather(dayArray[8]);

			WeatherFragment frag10 = (WeatherFragment) getFragmentManager()
					.findFragmentById(R.id.day10fragment);
			frag10.updateWeather(dayArray[9]);

		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return rootIntent.onOptionsItemSelected(this, item);
	}

	public Drawable weatherImage(String condition) {
		Resources res = getResources();
		Drawable conditionDrawable = null;
		if (condition.contains("rain") || condition.contains("Rain")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_rain);
		}
		if (condition.contains("sun") || condition.contains("Sun")
				|| condition.contains("clear") || condition.contains("Clear")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_clear);
		}
		if (condition.contains("storm")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_storm);
		}

		if (condition.contains("cloudy") || condition.contains("Cloudy")
				|| condition.contains("Overcast") || condition.contains("Haze")
				|| condition.contains("haze") || condition.contains("fog")
				|| condition.contains("Fog")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_cloudy);
		}
		if (condition.contains("snow") || condition.contains("Snow")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_snow);
		}
		if (condition.contains("Partly")) {
			conditionDrawable = res
					.getDrawable(R.drawable.weather_partlycloudy);
		}
		return conditionDrawable;

	}

	private OnClickListener wifiListener = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		};
	};
}
