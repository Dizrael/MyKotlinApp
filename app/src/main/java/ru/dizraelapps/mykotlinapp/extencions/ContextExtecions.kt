package ru.dizraelapps.mykotlinapp.extencions

import android.content.Context

inline fun Context.dip(value: Int) = resources.displayMetrics.density * value