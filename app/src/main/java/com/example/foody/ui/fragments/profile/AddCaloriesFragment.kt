package com.example.foody.ui.fragments.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.foody.R
import com.example.foody.databinding.FragmentAddCaloriesBinding


class AddCaloriesFragment : Fragment() {

    private var _binding: FragmentAddCaloriesBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    // to get the view model itself not an instance of it
    // using by delegate
    private val sharedViewModel: ProfileSharedViewModel by activityViewModels()


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCaloriesBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("values", Context.MODE_PRIVATE)

        binding.addCalButton.setOnClickListener(View.OnClickListener {
            val amount: String = binding.caloriesText.text.toString()
            sharedViewModel.setCalories(amount)
            editor=sharedPreferences.edit()
            editor.putString("Cal Value",amount)
            editor.apply()
            it.findNavController()
                .navigate(R.id.action_addCaloriesFragment_to_profileFragment)

        })
        // Inflate the layout for this fragment
        return binding.root
    }


}