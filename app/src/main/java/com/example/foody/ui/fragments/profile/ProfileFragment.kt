package com.example.foody.ui.fragments.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.foody.R
import com.example.foody.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private lateinit var mAuth: FirebaseAuth


    // to get the view model itself not an instance of it
    // using by delegate
    // this view model used to save data from the 3 fragments Calories fragment
    // & weight Fragment & Profile Fragment
    private val sharedViewModel: ProfileSharedViewModel by activityViewModels()


    var glassesNumber: Int = 0
    val picImagesNum = 100
    private var imageUri: Uri? = null
    var imgUriString: String = ""


    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("values", Context.MODE_PRIVATE)
        mAuth = FirebaseAuth.getInstance()


        binding.apply {
            profileviewmodel = sharedViewModel


        }


        // returning the substring of email before '@' as a username because the name is null and the firbase data is stored in authentication
       val userName= mAuth.currentUser?.email
        val user = userName?.substringBefore("@")
        if (userName != null) {
            sharedViewModel.setUserName(user!!)
            editor = sharedPreferences.edit()
            editor.putString("UserName", user)
            editor.apply()
        }

        if (sharedPreferences.all.isNotEmpty()) {
            sharedPreferences.getString("Cal Value", "")?.let { sharedViewModel.setCalories(it) }
            sharedPreferences.getString("weight value", "")?.let { sharedViewModel.setWeight(it) }
            sharedPreferences.getString("glasses value", null)?.let {
                it.toIntOrNull()?.let { it1 -> sharedViewModel.setGlassesNumber(it1) }
            }
            sharedPreferences.getString("UserName","")?.let {
                    sharedViewModel.setUserName(it)

            }
        }

        // Open Add calories fragment  when click the view Button
        binding.buttonView.setOnClickListener(View.OnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_addCaloriesFragment)
        })
        // Open add activity when click the add Button
        binding.buttonAdd.setOnClickListener(View.OnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_weightAddFragment)

        })

        binding.profilePic.setOnClickListener(View.OnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, picImagesNum)
        })
        // action on inc button
        binding.buttonInc.setOnClickListener(View.OnClickListener {
            var gNumber = sharedViewModel.glassesNumber.value?.toInt()
            if (gNumber != null) {
                gNumber++
                sharedViewModel.setGlassesNumber(gNumber)
                editor = sharedPreferences.edit()
                editor.putString("glasses value", gNumber.toString())
                editor.apply()
                glasses_num.text = gNumber.toString()
            }

        })
        // action on dec button
        binding.buttonDec.setOnClickListener(View.OnClickListener {
            var gNumber = sharedViewModel.glassesNumber.value?.toInt()
            if (gNumber == 0) {
                sharedViewModel.setGlassesNumber(0)
                glasses_num.text = ""
                editor = sharedPreferences.edit()
                editor.putString("glasses value", binding.glassesNum.text as String)
                editor.apply()
            } else {
                if (gNumber != null) {
                    gNumber--
                    sharedViewModel.setGlassesNumber(gNumber)
                    editor = sharedPreferences.edit()
                    editor.putString("glasses value", gNumber.toString())
                    editor.apply()
                    glasses_num.text = gNumber.toString()
                }

            }

        })

//
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == picImagesNum) {
            imageUri = data?.data
            editor = sharedPreferences.edit()
            editor.putString("image uri", imageUri.toString())
            editor.apply()
            binding.profilePic.setImageURI(imageUri)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }




}




