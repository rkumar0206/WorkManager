package com.RohitTheBest.workmanagerinandroid.wokManager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.RohitTheBest.workmanagerinandroid.other.Constants.NUM1KEY
import com.RohitTheBest.workmanagerinandroid.other.Constants.NUM2KEY
import com.RohitTheBest.workmanagerinandroid.other.Constants.OUTPUT_VALUE_KEY

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    val TAG = "MyWorker"
    override fun doWork(): Result {

        val data = inputData

        //Receiving Data Came from MainActivity
        val a = data.getInt(NUM1KEY, 0)
        val b = data.getInt(NUM2KEY, 0)

        Log.d(TAG, "Adding  $a and $b = ${a + b}")

        val dataOutput = Data.Builder()
            .putInt(OUTPUT_VALUE_KEY, a + b)
            .build()

        //Retuning the output of the data.
        return Result.success(dataOutput)
    }
}