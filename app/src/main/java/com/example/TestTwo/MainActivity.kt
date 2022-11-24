package com.example.TestTwo


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.TestTwo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
 private val requestReadSms:Int = 2

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS),requestReadSms)
        }else{
            readSmsMessages("", "address LIKE '${getString(R.string.mpesa)}'")
        }
        binding.mpesa.setOnClickListener {
            readSmsMessages("", "address LIKE '${getString(R.string.mpesa)}'")
        }
        binding.allsms.setOnClickListener {
            readSmsMessages("inbox",null)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestReadSms) readSmsMessages("",null)
    }

    private fun readSmsMessages(uriString: String, selection: String?) {
    val smsList = ArrayList<SmsData>()
        val cursor = contentResolver.query(
            Uri.parse("content://sms/$uriString"),
            null,
            selection,
            null,
            null
        )
        if (cursor!!.moveToFirst()){
            val nameId = cursor.getColumnIndex("address")
            val messageId = cursor.getColumnIndex("body")
            val dateId = cursor.getColumnIndex("date")
            do {
                val dateString = cursor.getString(dateId)
                smsList.add(SmsData(cursor.getString(nameId), Date(dateString.toLong()).toString(),cursor.getString(messageId)))
            }while (cursor.moveToNext())
        }
        cursor.close()
        binding.listView.layoutManager = LinearLayoutManager(this)
        val adapter = SmsAdapter(smsList,this)
        binding.listView.adapter = adapter

    }


}