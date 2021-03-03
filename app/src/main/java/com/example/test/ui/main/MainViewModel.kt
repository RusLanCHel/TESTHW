package com.example.test.ui.main

import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList

class MainViewModel(private val recyclerViewDataFilm: MutableLiveData<ArrayList<Film>> = MutableLiveData()) : ViewModel() {
    private lateinit var filmArrayList: ArrayList<Film>
    lateinit var filmDTO: FilmDTO

    @RequiresApi(Build.VERSION_CODES.N)
    fun getData(): LiveData<ArrayList<Film>>{
        getDataFromLocalSource()
        return recyclerViewDataFilm
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getDataFromLocalSource(){
        val uri = URL("https://api.themoviedb.org/3/movie/551?api_key=432640e9e1077e72bae8d8fb5a705171")
        lateinit var urlConnection: HttpsURLConnection
        val handler = Handler()
        Thread {
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = "GET"
                    readTimeout = 10000
                    //addRequestProperty("12345", "432640e9e1077e72bae8d8fb5a705171")
                }
                filmDTO = Gson().fromJson(getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))),
                        FilmDTO::class.java)
                handler.post {
                    filmArrayList = ArrayList(listOf(Film(filmDTO.original_title, 1)))
                    recyclerViewDataFilm.postValue(filmArrayList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if(null != urlConnection){
                    urlConnection.disconnect()
                }
            }
        }.start()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String{
        return reader.lines().collect(Collectors.joining("\n"))
    }

}