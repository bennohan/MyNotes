package com.example.mynotes.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.base.adapter.CoreListAdapter.Companion.get
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.example.mynotes.R
import com.example.mynotes.base.BaseFragment
import com.example.mynotes.data.Note
import com.example.mynotes.data.UserDao
import com.example.mynotes.data.constant.Cons
import com.example.mynotes.databinding.FragmentHomeBinding
import com.example.mynotes.databinding.ItemNoteBinding
import com.example.mynotes.ui.addNote.AddActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    SearchView.OnQueryTextListener {

    @Inject
    lateinit var userDao: UserDao

    private val viewModel by activityViewModels<HomeViewModel>()

    private var note = ArrayList<Note?>()
    private var noteAll = ArrayList<Note?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvNote = view.findViewById<RecyclerView>(R.id.rv_note)

        //SearchView Function
        binding?.searchView?.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()) {
                val filter = noteAll.filter { it?.titile?.contains("$text", true) == true }
//                val filteringData =
//                    noteAll.filter { it?.note?.contains(text.toString(), true) == true }
                Log.d("CekFilter", "Keyword $text Data : $filter")
                note.clear()
//                note.addAll(filter)
                filter.forEach {
                    note.add(it)
                }
                binding?.rvNote?.adapter?.notifyDataSetChanged()
                binding?.rvNote?.adapter?.notifyItemInserted(0)

            } else {
                note.clear()
                binding?.rvNote?.adapter?.notifyDataSetChanged()
                note.addAll(noteAll)
                Log.d("ceknoteall", "noteall : $noteAll")
                binding?.rvNote?.adapter?.notifyItemInserted(0)
            }
        }

        observe()
        getNote()

        //Adapter Recycler View
        rvNote.adapter = CoreListAdapter<ItemNoteBinding, Note>(R.layout.item_note)
            .initItem(note) { position, data ->
                activity?.tos("tes")
                activity?.openActivity<AddActivity> {
                    putExtra(Cons.NOTE.NOTE, data)
                }
            }
    }


    //Get Note Function
    private fun getNote() {
        viewModel.getNote()
        activity?.tos("Note Loaded")
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.note.observe(requireActivity()) {

                note.clear()
                noteAll.clear()

                note.addAll(it)
                noteAll.addAll(it)
                binding?.rvNote?.adapter?.notifyDataSetChanged()
                binding?.rvNote?.adapter?.notifyItemInserted(0)
            }

            viewModel.apiResponse.collect { it ->
//                    activity.tos("test")
                when (it.status) {
                    ApiStatus.SUCCESS -> {
                        binding?.rvNote?.adapter?.get()?.removeNull()
                    }
                    else -> {

                    }
                }
            }
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("Keyword", "$newText")
        return false
    }

}