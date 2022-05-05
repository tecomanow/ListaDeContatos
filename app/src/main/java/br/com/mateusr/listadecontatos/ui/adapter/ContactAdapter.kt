package br.com.mateusr.listadecontatos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.mateusr.listadecontatos.R
import br.com.mateusr.listadecontatos.data.model.Contact
import br.com.mateusr.listadecontatos.databinding.AdapterContactBinding

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.MyContactViewHolder>() {

    private val contacts = ArrayList<Contact>()
    var listenerClick : (contact : Contact) -> Unit = {}

    inner class MyContactViewHolder(private val binding: AdapterContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(contact : Contact){
                binding.textViewNameAdapter.text = contact.name
                binding.textViewEmailAdapter.text = contact.email

                binding.constraintContactAdapter.setOnClickListener {
                    listenerClick(contact)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<AdapterContactBinding>(inflater, R.layout.adapter_contact, parent, false)
        return MyContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    fun setContactList(contactsList : List<Contact>){
        contacts.clear()
        contacts.addAll(contactsList)
    }

}