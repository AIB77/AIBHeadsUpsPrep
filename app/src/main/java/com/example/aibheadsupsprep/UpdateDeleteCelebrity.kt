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

class UpdateDeleteCelebrity : AppCompatActivity()
{
     lateinit var btDelete: Button
     lateinit var btUpdate: Button
     lateinit var btBack: Button
     lateinit var etName: EditText
     lateinit var etTaboo1: EditText
     lateinit var etTaboo2: EditText
     lateinit var etTaboo3: EditText
     private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }
     private var celebrityID = 0
     override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete_celebrity)

        btUpdate = findViewById(R.id.btUpdate)
        btDelete = findViewById(R.id.btDelete)
        btBack = findViewById(R.id.btUpdateBack)
        etName = findViewById(R.id.etUpdateName)
        etTaboo1 = findViewById(R.id.etUpdateTaboo1)
        etTaboo2 = findViewById(R.id.etUpdateTaboo2)
        etTaboo3 = findViewById(R.id.etUpdateTaboo3)
        celebrityID = intent.extras!!.getInt("celebrityID", 0)

        getCelebrity()

        btDelete.setOnClickListener { deleteCelebrity() }

        btUpdate.setOnClickListener { updateCelebrity() }

        btBack.setOnClickListener {

            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getCelebrity() {

        apiInterface.getCelebrity(celebrityID).enqueue(object : Callback<Celebrity> {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {

                val celebrity = response.body()!!
                etName.setText(celebrity.name)
                etTaboo1.setText(celebrity.taboo1)
                etTaboo2.setText(celebrity.taboo2)
                etTaboo3.setText(celebrity.taboo3)
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {

                Toast.makeText(this@UpdateDeleteCelebrity,
                    "Something went wrong",
                    Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun deleteCelebrity() {


        apiInterface.deleteCelebrity(celebrityID).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                Toast.makeText(this@UpdateDeleteCelebrity, "Celebrity Deleted", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(this@UpdateDeleteCelebrity,
                    "Something went wrong",
                    Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun updateCelebrity() {


        apiInterface.updateCelebrity(
            celebrityID,
            Celebrity(
                etName.text.toString(),
                etTaboo1.text.toString(),
                etTaboo2.text.toString(),
                etTaboo3.text.toString(),
                celebrityID
            )).enqueue(object : Callback<Celebrity> {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {

                Toast.makeText(this@UpdateDeleteCelebrity, "Celebrity Updated", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {

                Toast.makeText(this@UpdateDeleteCelebrity,
                    "Something went wrong",
                    Toast.LENGTH_LONG).show()
            }

        })
    }


}