package com.example.mynotes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home){

    @Inject
    lateinit var userDao: UserDao

//    private val runnable by lazy {
//        Runnable {
////            refreshData()
//        }
//    }

//    private val handler = Handler(Looper.getMainLooper())

    private var keyword: String? = null

    private val viewModel by activityViewModels<HomeViewModel>()

    private var note = ArrayList<Note?>()

    private lateinit var rvNote: View

    private lateinit var selectedNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvNote = view.findViewById<RecyclerView>(R.id.rv_note)
//        val searchView = view.findViewById<SearchView>(R.id.searchView)

        getNote()


        //Adapter Recycler View
        rvNote.adapter = CoreListAdapter<ItemNoteBinding, Note>(R.layout.item_note)
            .initItem(note) { position, data ->
                activity?.tos("tes")
                activity?.openActivity<AddActivity> {
                    putExtra(Cons.NOTE.NOTE, data)
                }

            }


        lifecycleScope.launch {
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

//        val swipeToDeleteCallBack = object : SwipeToDeleteCallBack(){
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
////               viewModel.deletenote()
//            }
//        }
//
//        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
//        itemTouchHelper.attachToRecyclerView(rvNote)

    }
//    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
//        holder.notes_layout.setOnLongClickListener {
//            listener.onLongItemClicked(NotesList[holder.adapterPosition],holder.notes_layout)
//            true
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

//     fun onLongItemCLicked(note: Note, cardView: CardView) {
//        selectedNote = note
//        popUpDisplay(cardView)
//    }
//
//    private fun popUpDisplay(cardView: CardView) {
//        val popup = PopupMenu(context, cardView)
//        popup.setOnMenuItemClickListener(this@HomeFragment)
//        popup.inflate(R.menu.pop_up_menu)
//        popup.show()
//    }


    private fun getNote() {
        viewModel.getNote()
        activity?.tos("Note Loaded")
    }

//    override fun onMenuItemClick(item: MenuItem?): Boolean {
//        if (item?.itemId == R.id.itm_delete_note) {
//
//            return true
//        }
//        return false
//    }
//
//    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val notes_layout = itemView.findViewById<CardView>(R.id.item_note)
//    }
//
//    interface NotesClickListener{
//        fun onItemClicked(note: Note)
//        fun onLongItemClicked(note: Note, cardView: CardView)
//    }

//    private fun popupMenu() {
//        val popupMenu = PopupMenu(context, rvNote)
//        popupMenu.inflate(R.menu.pop_up_menu)
//        popupMenu.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.itm_delete_note -> {
//                    activity?.tos("note deleted")
//                    true
//                }
//
//
//                else -> true
//
//            }
//        }
//
//        rvNote.setOnLongClickListener {
//
//            try {
//                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
//                popup.isAccessible = true
//                val menu = popup.get(popupMenu)
//                menu.javaClass
//                    .getDeclaredMethod("seticon", Boolean::class.java)
//                    .invoke(menu, true)
//
//            } catch (e:Exception){
//                e.printStackTrace()
//            } finally {
//                popupMenu.show()
//            }
//
//            true
//
//        }
//    }


}