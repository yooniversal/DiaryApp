package com.example.newProject.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation
import com.example.newProject.R
import com.example.newProject.databinding.FragmentAddNoteBinding
import com.example.newProject.db.Note
import com.example.newProject.db.NoteDatabase
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) //set available option menu
        binding = FragmentAddNoteBinding.inflate(inflater) //add this as the return value for the onCreateView() method return binding.root
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //내용 불러오기
        arguments?.let{
            note = AddNoteFragmentArgs.fromBundle(it).note
            binding.editTextDate.setText(note?.date)
            binding.editTextTitle.setText(note?.title)
            binding.editTextNote.setText(note?.note)
        }

        binding.buttonSave.setOnClickListener { view ->
            val noteDate = binding.editTextDate.text.toString().trim()
            val noteTitle = binding.editTextTitle.text.toString().trim()
            val noteBody = binding.editTextNote.text.toString().trim()

            //날짜 예외처리
            if(noteDate.isEmpty()){
                binding.editTextDate.error = "날짜를 입력해주세요."
                binding.editTextDate.requestFocus()
                return@setOnClickListener
            }
            if(!checkDate(noteDate)){
                binding.editTextDate.error = "날짜 형식에 맞춰서 입력해주세요. (YYYY/MM/DD)"
                binding.editTextDate.requestFocus()
                return@setOnClickListener
            }

            if(noteTitle.isEmpty()){
                binding.editTextTitle.error = "제목을 입력해주세요."
                binding.editTextTitle.requestFocus()
                return@setOnClickListener
            }
            if(noteTitle.length > 22){
                binding.editTextTitle.error = "제목은 22자까지 가능합니다."
                binding.editTextTitle.requestFocus()
                return@setOnClickListener
            }

            if(noteBody.isEmpty()){
                binding.editTextNote.error = "내용을 입력해주세요."
                binding.editTextNote.requestFocus()
                return@setOnClickListener
            }

            launch {
                context?.let{
                    val mNote = Note(noteDate, noteTitle, noteBody)

                    //새 파일 저장
                    if(note == null){
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("저장이 완료되었습니다.")
                    }
                    //업데이트
                    else{
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("업데이트가 완료되었습니다.")
                    }

                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }

        }
    }

    private fun checkDate(s: String): Boolean {
        if(s.length != 10 || s[4] != '/' || s[7] != '/') return false

        if(s[5] <= '1'){
            if(s[5] == '1' && s[6] >= '3') return false
        }
        else return false

        if(s[8] == '3'){
            if(s[9] >= '2') return false
        }
        else if(s[8] > '4') return false

        return true
    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("정말로 삭제하시겠습니까?")
            setMessage("삭제된 파일은 되돌릴 수 없습니다.")
            setPositiveButton("Yes") { _, _ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Delete -> if(note != null) deleteNote() else context?.toast("삭제할 수 없습니다.")
        }
        return super.onOptionsItemSelected(item)
    }

    //메뉴 구현
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }
}