package com.arpit.notesapp2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.arpit.notesapp2.R
import com.arpit.notesapp2.db.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(private val notes : List<Note>): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_layout , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.view.tvTitle.text = notes[position].title
        holder.view.tvNote.text = notes[position].note

        holder.view.setOnClickListener {
          val action =HomeFragmentDirections.actionHomeFragmentToAddNoteFragment()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount()= notes.size
}