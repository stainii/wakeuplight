package stijnhooft.be.wakeuplight.ui

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.AsyncTask
import stijnhooft.be.wakeuplight.R

class SoundHelper(private val context: Context) {

    private val mediaPlayer = MediaPlayer()
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var originalVolume = determineOriginalVolume()

    fun ringAlarm() {
        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        object : AsyncTask<Void, Void, Boolean>() {
            override fun doInBackground(vararg voids: Void): Boolean? {
                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.reset()
                    mediaPlayer.release()
                    audioManager.setStreamVolume(originalVolume, originalVolume, 0)
                }

                mediaPlayer.setOnPreparedListener { mediaPlayer.start() }

                val assetFileDescriptor =
                    context.getResources().openRawResourceFd(R.raw.alarm)
                        ?: return false
                mediaPlayer.setDataSource(
                    assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength()
                )
                assetFileDescriptor.close()

                    mediaPlayer.setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )

                mediaPlayer.setVolume(1.0f, 1.0f)
                mediaPlayer.prepare()
                return true
            }

        }.execute()
    }

    fun turnOffAlarm() {
        this.mediaPlayer.stop()
    }

    private fun determineOriginalVolume(): Int {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }


}