package com.arpit.notesapp2.ui

import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.arpit.notesapp2.R
import com.arpit.notesapp2.db.Note
import com.arpit.notesapp2.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNoteFragment : BaseFragment() {

   private var noteClass : Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // use activity instead of requireactivity

        arguments?.let {
            noteClass = AddNoteFragmentArgs.fromBundle(it).note
            etTitle.setText(noteClass?.title)
            etNote.setText(noteClass?.note)


        }


      buSave.setOnClickListener {view->
          val noteTitle = etTitle.text.toString().trim()
          val noteBody = etNote.text.toString().trim()

          if(noteTitle.isEmpty()){
              etTitle.error = "Title Required"
              etTitle.requestFocus()
              return@setOnClickListener
          }

          if(noteBody.isEmpty()){
              etNote.error = "Note Required"
              etNote.requestFocus()
              return@setOnClickListener
          }

          launch {

                context?.let {
                    val mNote = Note(noteTitle , noteBody)

                    if (noteClass == null){
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")
                    } else{
                        mNote.id = noteClass!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note Updated")
                    }


                   val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
                    Navigation.findNavController(view).navigate(action)
                }
          }


      }


    }




    private fun deleteNote() {

        launch {
                    context?.let { NoteDatabase(it).getNoteDao().deleteNote(noteClass!!) }
                    val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
                   Navigation.findNavController(requireView()).navigate(action)
                }
//
//        AlertDialog.Builder(requireContext()).apply {
//            setTitle("Are You Sure?")
//            setMessage("Delete the Note")
//            setPositiveButton("Yes") { _, _ ->
//                launch {
//                    NoteDatabase(context).getNoteDao().deleteNote(noteClass!!)
//                    val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
//                   Navigation.findNavController(requireView()).navigate(action)
//                }
//            }
//            setNegativeButton("No") { _, _ ->
//
//            }
//        }.create()

        }
//       android.app.AlertDialog.Builder(context).apply {
//            setTitle("Are You Sure?")
//            setMessage("Delete the Note")
//            setPositiveButton("Yes") { _, _ ->
//                launch {
//                    NoteDatabase(context).getNoteDao().deleteNote(noteClass!!)
//                    val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
//                   Navigation.findNavController(requireView()).navigate(action)
//                }
//            }
//            setNegativeButton("No") { _, _ ->
//
//            }
//        }.create()



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> if (noteClass != null){
                deleteNote()
            } else {
                context?.toast("Cannot Delete")
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu , menu)
    }


}