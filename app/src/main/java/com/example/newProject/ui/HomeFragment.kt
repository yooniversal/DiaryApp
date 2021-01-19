package com.example.newProject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.newProject.R
import com.example.newProject.databinding.FragmentHomeBinding
import com.example.newProject.db.NoteDatabase
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater) //add this as the return value for the onCreateView() method return binding.root
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerViewNotes.setHasFixedSize(true)
//        binding.recyclerViewNotes.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) // 가로 3개씩 정렬

        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                binding.recyclerViewNotes.adapter = NotesAdapter(notes)
            }
        }

        binding.buttonAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)
        }
    }
}