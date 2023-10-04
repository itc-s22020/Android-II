package jp.ac.it_college.std.s22020.asyncsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s22020.asyncsample.databinding.ActivityMainBinding
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    companion object {
        private const val DEBUG_TAG = "AsyncSample"
        private const val WEATHER_INFO_URL =
            "https://api.openweathermap.org/data/2.5/weather?lang=ja"
        private const val API_ID = BuildConfig.APP_ID
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCityList.apply {
            adapter = CityAdapter {
                receivWeatherInfo(it.q)
            }
            layoutManager = LinearLayoutManager(context)
        }
    }

    @UiThread
    private fun receivWeatherInfo(q : String) {
        val url = "$WEATHER_INFO_URL&q&appid=$API_ID"
        val executorService = Executors.newSingleThreadExecutor()
        val backgroundReceiver = WeatherInfoBackgroundReceiver()
        val future = executorService.submit(backgroundReceiver)
        val result = future.get()
    }
    private class WeatherInfoBackgroundReceiver() : Callable<String> {
        @WorkerThread
        override fun call() : String {
            return "hoge"
        }
    }
}
