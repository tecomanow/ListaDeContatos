package br.com.mateusr.listadecontatos.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mateusr.listadecontatos.App
import br.com.mateusr.listadecontatos.R
import br.com.mateusr.listadecontatos.data.model.Contact
import br.com.mateusr.listadecontatos.databinding.ActivityMainBinding
import br.com.mateusr.listadecontatos.ui.adapter.ContactAdapter
import br.com.mateusr.listadecontatos.viewmodel.MainViewModel
import br.com.mateusr.listadecontatos.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private val adapter by lazy { ContactAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModelFactory = MainViewModelFactory((application as App).repository)
        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        iniRecyclerView()
        initObserver()
        setNoDataVisibility(false)
    }

    private fun iniRecyclerView() {
        adapter.listenerClick = {
            viewModel.setContactUpdateOrDelete(it)
        }
        binding.apply {
            recyclerViewContatos.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            recyclerViewContatos.setHasFixedSize(true)
            recyclerViewContatos.adapter = adapter
        }
    }

    private fun initObserver() {

        viewModel.getAllContact().observe(this) {
            it?.let {
                updateRecyclerView(it)
            }
        }

        viewModel.message.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setNoDataVisibility(visibility: Boolean) {
        if (visibility) {
            binding.linearLayoutNoContact.visibility = View.VISIBLE
        } else {
            binding.linearLayoutNoContact.visibility = View.GONE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(list: List<Contact>) {
        if (list.isEmpty()) {
            setNoDataVisibility(true)
        } else {
            setNoDataVisibility(false)
        }
        adapter.setContactList(list)
        adapter.notifyDataSetChanged()
    }

}