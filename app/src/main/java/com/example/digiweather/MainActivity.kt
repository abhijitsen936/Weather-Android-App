package com.example.digiweather
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject





class MainActivity : AppCompatActivity() {

    private lateinit var city : TextView
    private lateinit var latt : TextView
    private lateinit var longg : TextView
    private lateinit var weather : TextView
    private lateinit var temp : TextView
    private lateinit var pressure : TextView
    private lateinit var humidity : TextView
    private lateinit var speed : TextView
    private lateinit var feel : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         city= findViewById<TextView>(R.id.city)
        latt=findViewById(R.id.latti)
        longg=findViewById(R.id.longi)
        weather=findViewById(R.id.weather)
        temp=findViewById(R.id.temp)
        pressure=findViewById(R.id.pressure)
        humidity=findViewById(R.id.humidity)
        speed=findViewById(R.id.speed)
        feel=findViewById(R.id.feel)



        val lat=intent.getStringExtra("lat")
        val long=intent.getStringExtra("long")
//        window.statusBarColor= Color.parseColor("#1383C3")


        getJsonData(lat, long)


    }





    private fun getJsonData(lat:String?, long:String?){
        val API_KEY="3f420c29ae7d78541efb5ef4a18e2a1c"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?" +
                "lat=${lat}&lon=${long}&appid=${API_KEY}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
            setValues(response)
            },
            { Toast.makeText(this, "error", Toast.LENGTH_LONG).show() })

        queue.add(jsonRequest)
    }

    private  fun setValues(response: JSONObject){
        city.text=response.getString("name")
        var lat=response.getJSONObject("coord").getString("lat")
        var long=response.getJSONObject("coord").getString("lon")
        latt.text=lat
        longg.text=long
        weather.text=response.getJSONArray("weather").getJSONObject(0).getString("main")
        var tempo=response.getJSONObject("main").getString("temp")
        tempo=((((tempo).toFloat()-273.15)).toInt()).toString()
        var feels=response.getJSONObject("main").getString("feels_like")
        feels=((((feels).toFloat()-273.15)).toInt()).toString()
        feel.text="feels like ${feels}°C"

        temp.text="${tempo}°C"
        pressure.text=response.getJSONObject("main").getString("pressure")
        humidity.text=response.getJSONObject("main").getString("humidity")+"+"
        speed.text=response.getJSONObject("wind").getString("speed")


    }
}