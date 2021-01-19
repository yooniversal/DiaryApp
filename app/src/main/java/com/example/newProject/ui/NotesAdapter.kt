package com.example.newProject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.newProject.R
import com.example.newProject.databinding.NoteLayoutBinding
import com.example.newProject.db.Note

class NotesAdapter(val notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.textDate.text = notes[position].date
        holder.binding.textViewTitle.text = notes[position].title
//        holder.binding.textViewNote.text = notes[position].note //preview에 note 표시 비활성화

        //Update existed Note
        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionAddNote()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)
        }
    }

    class NoteViewHolder(var binding: NoteLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)
}