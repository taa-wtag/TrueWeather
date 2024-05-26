package com.rektstudios.trueweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rektstudios.trueweather.databinding.FragmentCitiesBinding


class CitiesFragment : Fragment() {
    private lateinit var binding: FragmentCitiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCitiesBinding.inflate(inflater,container,false)
        return binding.root
    }
}