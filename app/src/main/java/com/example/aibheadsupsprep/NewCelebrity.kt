package com.example.aibheadsupsprep

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aibheadsupsprep.model.Celebrity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewCelebrity : AppCompatActivity()
{
    private lateinit var etName: EditText
    private lateinit var etTaboo1: EditText
    private lateinit var btAdd: Button
    private lateinit var btBack: Button
    private lateinit var etTaboo2: EditText
    private lateinit var etTaboo3: EditText
    private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }



    private lateinit var existingCelebrities: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_celebrity)

        existingCelebrities = intent.extras!!.getStringArrayList("celebrityNames")!!

        etName = findViewById(R.id.etNewName)
        etTaboo1 = findViewById(R.id.etNewTaboo1)
        etTaboo2 = findViewById(R.id.etNewTaboo2)
        etTaboo3 = findViewById(R.id.etNewTaboo3)
        btAdd = findViewById(R.id.btNewAdd)
        btBack = findViewById(R.id.btNewBack)


        btAdd.setOnClickListener { addCelebrity() }


        btBack.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addCelebrity(){

        apiInterface.addCelebrity(
            Celebrity(
                etName.text.toString().capitalize(),
                etTaboo1.text.toString(),
                etTaboo2.text.toString(),
                etTaboo3.text.toString(),
                0
            )
        ).enqueue(object: Callback<Celebrity>
        {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>)
            {
                if(!existingCelebrities.contains(etName.text.toString()))
                {
                    intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }else
                {
                    Toast.makeText(this@NewCelebrity, "Celebrity Already Exists", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable)
            {

                Toast.makeText(this@NewCelebrity, "Unable to get data", Toast.LENGTH_LONG).show()
            }
        })
    }
}