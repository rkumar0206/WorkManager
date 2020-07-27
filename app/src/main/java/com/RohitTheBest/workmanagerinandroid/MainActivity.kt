package com.RohitTheBest.workmanagerinandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.RohitTheBest.workmanagerinandroid.other.Constants.NUM1KEY
import com.RohitTheBest.workmanagerinandroid.other.Constants.NUM2KEY
import com.RohitTheBest.workmanagerinandroid.other.Constants.OUTPUT_VALUE_KEY
import com.RohitTheBest.workmanagerinandroid.wokManager.MyWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Making data for sending
        val data = Data.Builder()
            .putInt(NUM1KEY, 40)
            .putInt(NUM2KEY, 60)
            .build()


        //Making Constraints for WorkManager
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //making one time request
        val oneTimeWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(data)  //Sending the data
            .setConstraints(constraints)  //Setting Constraints for work to be done.
            .build()

        //Starting the work
        WorkManager.getInstance(applicationContext)
            .enqueue(oneTimeWorkRequest)


        //Getting the liveData of the current state of the work
        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this, Observer {

                if (it.state.isFinished) {

                    //Receiving the output came from MyWorker Class
                    val output = it.outputData.getInt(OUTPUT_VALUE_KEY, 5)
                    Toast.makeText(this@MainActivity, "$output", Toast.LENGTH_LONG).show()
                }
                mainTextView.append("${it.state.name}\n")
            })
    }
}