package com.example.homework_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

//cd android_dev
//
//# Remove all submodules
//git submodule status | awk '{print $2}' | xargs -I {} bash -c 'git submodule deinit {} && git rm {} && rm -rf .git/modules/{}'
//
//# Add all files to the repository
//git add .
//
//# Commit and push the changes
//git commit -m "Removed all submodules and added files"
//git push origin master