package com.example.android2021task5.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.android2021task5.R
import com.example.android2021task5.databinding.FragmentCatImageBinding
import com.example.android2021task5.databinding.MainFragmentBinding


class CatImageFragment : Fragment() {

    private var _binding: FragmentCatImageBinding? = null
    private val binding get() = _binding!!

    private var imgUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imgUrl = it.getString("imgUrl")
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_cat_image, container, false)
        _binding = FragmentCatImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.root)
            .load(imgUrl)
            //.placeholder(R.drawable.ic_progress)
            .into(binding.fullCatImage)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CatImageFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}