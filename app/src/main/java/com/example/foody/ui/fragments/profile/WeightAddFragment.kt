package com.example.foody.ui.fragments.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foody.R
import com.example.foody.databinding.FragmentProfileBinding
import com.example.foody.databinding.FragmentWeightAddBinding


class WeightAddFragment : Fragment() {

    private var _binding: FragmentWeightAddBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    // to get the view model itself not an instance of it
    // using by delegate
    private  val sharedViewModel:ProfileSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentWeightAddBinding.inflate(inflater,container,false)
        sharedPreferences = requireActivity().getSharedPreferences("values", Context.MODE_PRIVATE)

        binding.addWeightButton.setOnClickListener(View.OnClickListener {
            val amount:String= binding.wText.text.toString()
            sharedViewModel.setWeight(amount)
            editor=sharedPreferences.edit()
            editor.putString("weight value",amount)
            editor.apply()
            it.findNavController().navigate(R.id.action_weightAddFragment_to_profileFragment)

        })

        // Inflate the layout for this fragment
        return binding.root
    }


}