package jp.ac.it_college.std.s22020.implicitintentsample

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import jp.ac.it_college.std.s22020.implicitintentsample.databinding.ActivityMainBinding
import java.net.URLEncoder
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var latitude = 0.0
    private var longitude = 0.0

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGrantedFineLocation =
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
        val isGrantedCoarseLocation =
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
        if (isGrantedFineLocation || isGrantedCoarseLocation) {
            requestLocationUpdates()
            return@registerForActivityResult
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btMapSearch.setOnClickListener(::opMapSearchButton)
        binding.btMapShowCurrent.setOnClickListener(::onMapShowCurrentButtonClick)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 5000
        ).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.also { location ->
                    latitude = location.latitude
                    longitude = location.longitude

                    binding.tvLatitude.text = latitude.toString()
                    binding.tvLongitude.text = longitude.toString()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestLocationUpdates()
    }

    private fun opMapSearchButton(view: View) {
        val searchWord = URLEncoder.encode(
            binding.eaSearchWord.text.toString(), "UTF-8"
        )
        val uri = Uri.parse("geo:0,0?q=${searchWord}")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun  onMapShowCurrentButtonClick(view: View) {
        val uri =Uri.parse("geo:${latitude},${longitude}")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun requestLocationUpdates() {
        val isGrantedFineLocation = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val isGrantedCoarseLocation = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (isGrantedFineLocation || isGrantedCoarseLocation) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                mainLooper
            )
            return
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}