package com.example.test.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R

class MainFragment : Fragment(), IRVOnItemClick{
    private lateinit var observer: Observer<ArrayList<Film>>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        return root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)

        observer = Observer {setupRecyclerView(it)}
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }


    fun setupRecyclerView(data: ArrayList<Film>){
        val linearLayoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter: RecyclerDataAdapter = RecyclerDataAdapter(data, this)

        recyclerView = requireActivity().findViewById(R.id.recyclerView)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun onItemClicked() {
        val intent = Intent(context, FilmInfo::class.java)
        startActivity(intent)
    }
}
