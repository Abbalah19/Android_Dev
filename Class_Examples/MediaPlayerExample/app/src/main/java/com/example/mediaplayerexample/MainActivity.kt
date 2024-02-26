package com.example.mediaplayerexample

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    private var myMediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /*
        A button for Starting sound.
        If it is the first time, it creates the MediaPlayer object with the song provided
    */
    fun playSound(view: View) {
        if (myMediaPlayer == null) {
            myMediaPlayer = MediaPlayer.create(this, R.raw.song)
        }
        // Starts or resumes playback.
        myMediaPlayer?.start()
    }

    /*
        A button for Pausing sound.
    */
    fun pauseSound(view: View) {
        myMediaPlayer?.pause()
    }

    /*
        A button for Stopping sound after it has been started or paused.
    */
    fun stopSound(view: View) {
        //myMediaPlayer?.stop() // rather stopping it, stopSong helper method is used to release the system resources used
        stopSong()
    }

    /*
        Helper function to handle stopping the sound
    */
    private fun stopSong(){
        if(myMediaPlayer != null){
            //myMediaPlayer?.stop()
            // Release is probably better option to release the system resources used
            myMediaPlayer?.release()
            myMediaPlayer = null
        }
    }

    /*
        Stop the song if onStop() is called
        You should release the MediaPlayer, because it makes little sense to hold on to it
        while your activity is not interacting with the user
    */
    override fun onStop() {
        super.onStop()
        stopSong()
    }

}