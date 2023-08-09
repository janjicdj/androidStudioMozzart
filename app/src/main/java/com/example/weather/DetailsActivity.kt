package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.weather.databinding.ActivityDetails2Binding
import com.example.weather.dto.WeatherTime
import android.content.Intent
import android.os.Parcelable


class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetails2Binding

    private lateinit var temperatureTextView: TextView
    private lateinit var windspeedTextView: TextView
    private lateinit var winddirectionTextView: TextView
    private lateinit var weathercodeTextView: TextView
    private lateinit var isDayTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var latitudeTextView: TextView
    private lateinit var longitudeTextView: TextView
    private lateinit var generationtimeMsTextView: TextView
    private lateinit var utcOffsetSecondsTextView: TextView
    private lateinit var timezoneTextView: TextView
    private lateinit var timezoneAbbreviationTextView: TextView
    private lateinit var elevationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetails2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        Log.i("des","PRE IF USLOV")

        val weatherTime = intent.getParcelableExtra<WeatherTime>("WeatherTime")
        if (weatherTime != null) {
            Log.i("des","************************IF USLOV")
            Log.i("des","$weatherTime")
            latitudeTextView = findViewById(R.id.latitudeTextView)
            longitudeTextView = findViewById(R.id.longitudeTextView)
            generationtimeMsTextView = findViewById(R.id.generationtimeMsTextView)
            utcOffsetSecondsTextView = findViewById(R.id.utcOffsetSecondsTextView)
            timezoneTextView = findViewById(R.id.timezoneTextView)
            timezoneAbbreviationTextView = findViewById(R.id.timezoneAbbreviationTextView)
            elevationTextView = findViewById(R.id.elevationTextView)
            temperatureTextView = findViewById(R.id.temperatureTextView)
            windspeedTextView = findViewById(R.id.windspeedTextView)
            winddirectionTextView = findViewById(R.id.winddirectionTextView)
            weathercodeTextView = findViewById(R.id.weathercodeTextView)
            isDayTextView = findViewById(R.id.isDayTextView)
            timeTextView = findViewById(R.id.timeTextView)

            latitudeTextView.text = weatherTime.latitude.toString()
            longitudeTextView.text = weatherTime.longitude.toString()
            generationtimeMsTextView.text = weatherTime.generationtimeMs.toString()
            utcOffsetSecondsTextView.text = weatherTime.utcOffsetSeconds.toString()
            timezoneTextView.text = weatherTime.timezone
            timezoneAbbreviationTextView.text = weatherTime.timezoneAbbreviation
            elevationTextView.text = weatherTime.elevation.toString()
            temperatureTextView.text = weatherTime.currentWeather?.temperature.toString()
            windspeedTextView.text = weatherTime.currentWeather?.windspeed.toString()
            winddirectionTextView.text = weatherTime.currentWeather?.winddirection.toString()
            weathercodeTextView.text = weatherTime.currentWeather?.weathercode.toString()
            isDayTextView.text = weatherTime.currentWeather?.isDay.toString()
            timeTextView.text = weatherTime.currentWeather?.time
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_back -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}