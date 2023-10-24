package jp.ac.it_college.std.s22020.cameraintentsample

import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import jp.ac.it_college.std.s22020.cameraintentsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result?.resultCode == RESULT_OK) {
            val bitmap = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra("data", Bitmap::class.java)
            } else {
                result.data?.getParcelableExtra<Bitmap>("data")
            }
            binding.ivCamera.setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}