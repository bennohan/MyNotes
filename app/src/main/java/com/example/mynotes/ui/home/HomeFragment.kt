package com.example.mynotes.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    private val runnable by lazy {
        Runnable {
            viewModel.getNote(keyword)
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private var keyword: String? = null

    private val viewModel by activityViewModels<HomeViewModel>()

    private var note = ArrayList<Note?>()
    private var noteAll = ArrayList<Note?>()

    private lateinit var rvNote: View
    private lateinit var selectedNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvNote = view.findViewById<RecyclerView>(R.id.rv_note)

        //SearchView Function
        binding?.searchView?.doOnTextChanged{text, start, before, count ->
            if (text.isNullOrEmpty()) {
                val filter = noteAll.filter { it?.titile?.contains("$text", true) == true }
                Log.d("CekFilter", "Keyword : $filter")
                note.clear()
                binding?.rvNote?.adapter?.notifyDataSetChanged()
                note.addAll(filter)
                binding?.rvNote?.adapter?.notifyItemInserted(0)

            } else {
                note.clear()
                binding?.rvNote?.adapter?.notifyDataSetChanged()
                note.addAll(noteAll)
                Log.d("ceknoteall", "noteall : $noteAll")
                binding?.rvNote?.adapter?.notifyItemInserted(0)
            }
        }
//        binding?.searchView?.setOnQueryTextListener(this)
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

//        //Search v2 (eddit text)
//        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//        })

        /*lifecycleScope.launch {
            viewModel.note.observe(requireActivity()) { dataNote ->
                if (dataNote.isEmpty()) {
                    activity?.tos("dataKosongFR")
                } else {
//                    activity?.tos("dataAdaFR")
                }
                note.clear()
                note.addAll(dataNote)

                binding?.rvNote?.adapter?.notifyDataSetChanged()
            }
        }*/
    }


    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

        //working on it

    }*/


    private fun getNote() {
        viewModel.getNote()
        activity?.tos("Note Loaded")
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.note.observe(requireActivity()) {
                val shoreName = it.sortedBy { short ->
                    short.idRoom != 1
                }
//            val filter = it.filter { it.idRoom != 1 }
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