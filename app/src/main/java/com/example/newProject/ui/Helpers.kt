package com.example.newProject.ui

import android.content.Context
import android.widget.Toast

//Toast 출력을 보다 간단하게
fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()