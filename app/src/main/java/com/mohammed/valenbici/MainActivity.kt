package com.mohammed.valenbici

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohammed.valenbici.model.Station
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StationAdapter
    private val stationList = mutableListOf<Station>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "ValenBici"

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StationAdapter(stationList)
        recyclerView.adapter = adapter

        fetchValenbisiData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = "Buscar"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                Toast.makeText(this, "Actualizando datos...", Toast.LENGTH_SHORT).show()
                fetchValenbisiData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchValenbisiData() {
        Thread {
            try {
                val apiUrl =
                    "https://valencia.opendatasoft.com/api/records/1.0/search/?dataset=valenbisi-disponibilitat-valenbisi-dsiponibilidad&rows=50&format=json"
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val data = connection.inputStream.bufferedReader().use { it.readText() }
                    Log.d("API_RESPONSE", data)
                    parseJson(data)
                } else {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Error en la conexión: $responseCode", Toast.LENGTH_SHORT).show()
                    }
                }

                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Error al descargar los datos", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun parseJson(jsonString: String) {
        try {
            val jsonObject = JSONObject(jsonString)
            val records = jsonObject.getJSONArray("records")
            val tempList = mutableListOf<Station>()

            for (i in 0 until records.length()) {
                val record = records.getJSONObject(i)
                val fields = record.getJSONObject("fields")

                val address = fields.optString("address", "Sin dirección")
                val availableBikes = fields.optInt("available", 0)
                val freeSpaces = fields.optInt("free", 0)

                val station = Station(address, availableBikes, freeSpaces)
                tempList.add(station)
            }

            runOnUiThread {
                stationList.clear()
                stationList.addAll(tempList)
                adapter.updateStations(stationList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            runOnUiThread {
                Toast.makeText(applicationContext, "Error al procesar los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
