package com.example.android2021task5.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android2021task5.R
import com.example.android2021task5.databinding.MainFragmentBinding
import com.example.android2021task5.interfaces.ICatImageListener
import com.example.android2021task5.ui.adapters.CatImageAdapter

class MainFragment : Fragment(), ICatImageListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var viewModel: MainViewModel? = null
    private val catImageAdapter = CatImageAdapter(this)

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.catImagesRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = catImageAdapter
        }

        viewModel?.catImages?.observe(
            viewLifecycleOwner,
            Observer {
                it ?: return@Observer
                catImageAdapter.submitList(it)
            }
        )

        viewModel?.isLoadingCats?.observe(
            viewLifecycleOwner,
            Observer {
                it ?: return@Observer
                binding.swiperefresh.isRefreshing = it
            }
        )

        binding.swiperefresh.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun loadNextPage() {
        viewModel?.loadNextPageCatImages()
    }

    override fun openImageFragment(imgUrl: String) {
        view?.findNavController()?.navigate(R.id.action_mainFragment_to_catImageFragment, bundleOf("imgUrl" to imgUrl))
    }
}
