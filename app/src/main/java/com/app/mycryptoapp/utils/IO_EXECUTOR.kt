package com.app.mycryptoapp.utils
import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Created by Giteeka on 11/22/2018
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 */
fun ioThread(f : () -> Unit) {
    IO_EXECUTOR.execute(f)
}