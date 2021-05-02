package com.bharath.codeJournals.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bharath.codeJournals.R
import com.bharath.codeJournals.adapter.AlbumsDisplayAdapter
import com.bharath.codeJournals.model.AlbumsModel
import com.bharath.codeJournals.viewModel.MainActivityViewModel
import com.bharath.codeJournals.viewModel.PageViewModel

/**
 * Created by Bharath KM on 2/5/21.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var gridView: GridView
    private lateinit var albumsList: List<AlbumsModel>
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        albumsList = arguments?.getParcelableArrayList<AlbumsModel>(ALBUMS_LIST)!!
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        gridView = root.findViewById(R.id.grid_view)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivityViewModel.getAlbumDetails(arguments?.getInt(ARG_SECTION_NUMBER).toString())
        mainActivityViewModel.dataModelObject2.observe(this, {
            gridView.adapter = AlbumsDisplayAdapter(activity!!, albumsList, it)
        })
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ALBUMS_LIST = "albums_list"

        @JvmStatic
        fun newInstance(
            sectionNumber: Int,
            albumsList: ArrayList<AlbumsModel>
        ): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putParcelableArrayList(ALBUMS_LIST, albumsList)
                }
            }
        }
    }
}