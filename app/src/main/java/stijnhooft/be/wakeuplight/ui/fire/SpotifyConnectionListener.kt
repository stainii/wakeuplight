package stijnhooft.be.wakeuplight.ui.fire

import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

class SpotifyConnectionListener(private val spotifyPlayer: SpotifyPlayer):
    Connector.ConnectionListener {

    companion object {
        private const val SPOTIFY_PLAYLIST_ID = "spotify:playlist:18wMGEYXOerJCUHxoBkygD"
    }

    override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
        spotifyPlayer.spotifyAppRemote = spotifyAppRemote
        spotifyAppRemote.playerApi.setShuffle(true)
        spotifyAppRemote.playerApi.play(SPOTIFY_PLAYLIST_ID)
    }

    override fun onFailure(throwable: Throwable) {
        throw throwable
    }

}