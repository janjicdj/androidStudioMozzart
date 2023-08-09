package com.example.weather

import android.Manifest
import android.R
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weather.api.NetworkClient
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.dto.WeatherTime
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var client = NetworkClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.button.setOnClickListener {
            Log.i("des", "usli smo")
            val latitude = binding.latitutetxt.text.toString()
            val longitude = binding.longitude.text.toString()
            if (latitude != "" && longitude != "" && latitude.toDouble() <= 90 && longitude.toDouble() < 180) {
                forecast(
                    binding.latitutetxt.text.toString().toDouble(),
                    binding.longitude.text.toString().toDouble()
                )
            } else {
                binding.label.text = "Nepravilan unos"
            }
        }

    }

    private val locationRequest = LocationRequest.create().apply {
        interval = 6000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(location: LocationResult) {
            super.onLocationResult(location)

            location.lastLocation?.latitude?.let { latitude ->
                location.lastLocation?.longitude?.let { longitude ->
                    forecast(latitude, longitude)
                }
            }
        }
    }

    private fun forecast(latitude: Double, longitude: Double) {
        client.getForecast(latitude, longitude)
            .enqueue(object : Callback<WeatherTime> {
                override fun onResponse(
                    call: Call<WeatherTime>,
                    response: Response<WeatherTime>
                ) {
                    binding.progress.visibility=View.GONE
                    if (response.isSuccessful) {
                        val data = response.body()
                        binding.label.text =
                            "${data?.currentWeather?.temperature} C"
                    } else {
                        binding.label.text =
                            "Response code: ${response.code()}, ${response.errorBody()}"
                    }
                }

                override fun onFailure(call: Call<WeatherTime>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    t.printStackTrace()
                }
            })
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            requestLocationUpdates()
        } else {
            showPermissionRationaleDialog()
        }
    }

    private fun showPermissionRationaleDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("Niste dali dozvolu za lokaciju. Mozete nastaviti rad samo u hand mode-u")
        builder.setTitle("Upozorenje")
        builder.setCancelable(false)
        builder.setPositiveButton("Ok") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationUpdates()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("des", "bilding.progress.visibility${binding.progress.visibility}")
            binding.progress.visibility= View.VISIBLE
            fusedLocationProviderClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            Log.i("des", "$locationRequest")
            Log.i("des", "$locationCallback")
        }
    }

    override fun onResume() {
        super.onResume()
        requestLocationPermission()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }
}
