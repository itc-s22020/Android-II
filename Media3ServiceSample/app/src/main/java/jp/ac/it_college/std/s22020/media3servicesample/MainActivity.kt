package jp.ac.it_college.std.s22020.media3servicesample

import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import jp.ac.it_college.std.s22020.media3servicesample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.play.setOnClickListener { playSample() }
        binding.pause.setOnClickListener { pauseSample() }
    }

    override fun onStart() {
        super.onStart()
        controllerFuture = MediaController.Builder(
            this,
            SessionToken(this, ComponentName(this, MediaPlaybackService::class.java))
        ).buildAsync()
    }

    override fun onStop() {
        MediaController.releaseFuture(controllerFuture)

        super.onStop()
    }

    private fun playSample() {
        val controller = this.controller ?: return

        controller.setMediaItem(
            MediaItem.fromUri("android.resource://${packageName}/${R.raw.mati}")
        )
        controller.seekToDefaultPosition(0)
        controller.play()
    }
    private fun pauseSample() {
        val controller = this.controller ?: return
        controller.stop()
    }
}