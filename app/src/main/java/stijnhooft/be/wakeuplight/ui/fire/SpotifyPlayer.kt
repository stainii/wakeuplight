package stijnhooft.be.wakeuplight.ui.fire

import android.content.Context
import android.media.AudioManager
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector.ConnectionListener
import com.spotify.android.appremote.api.SpotifyAppRemote


class SpotifyPlayer(private val context: Context) {

    internal lateinit var spotifyAppRemote: SpotifyAppRemote
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var originalVolume = determineOriginalVolume()

    companion object {
        private const val CLIENT_ID = "59304a968926469ea6403ea0531e94fa"
        private const val REDIRECT_URI = "http://stijnhooft.be/wake-up-light/"
    }

    fun play() {
        turnUpVolumeToMax()
        connectAndPlay()
    }

    private fun connectAndPlay() {
        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(context, connectionParams, SpotifyConnectionListener(this))
    }

    fun stop() {
        turnDownVolumeToOriginal()
        spotifyAppRemote.playerApi.pause()
        SpotifyAppRemote.disconnect(spotifyAppRemote)
    }

    private fun turnUpVolumeToMax() {
        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0)
    }

    private fun turnDownVolumeToOriginal() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0)
    }

    private fun determineOriginalVolume(): Int {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }
}

class SpotifyConnectionListener(private val spotifyPlayer: SpotifyPlayer): ConnectionListener {

    companion object {
        private const val SPOTIFY_PLAYLIST_ID = "spotify:playlist:18wMGEYXOerJCUHxoBkygD"
    }

    override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
        spotifyPlayer.spotifyAppRemote = spotifyAppRemote
        spotifyAppRemote.playerApi.play(SPOTIFY_PLAYLIST_ID)
    }

    override fun onFailure(throwable: Throwable) {
        throw throwable
    }

}