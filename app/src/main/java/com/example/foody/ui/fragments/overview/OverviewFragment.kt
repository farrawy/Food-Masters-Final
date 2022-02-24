package com.example.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.foody.R
import com.example.foody.models.Result
import com.example.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_overview.view.*
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        view.main_imageView.load(myBundle?.image)
        view.title_textView.text = myBundle?.title
        view.likes_textView.text = myBundle?.aggregateLikes.toString()
        view.time_textView.text = myBundle?.readyInMinutes.toString()
        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            view.summary_textView.text = summary
        }

        // Vegetarian Checkmarks
        if(myBundle?.vegetarian == true) {
            view.vegetarian_imageView.setColorFilter((ContextCompat.getColor(requireContext(), R.color.green)))
            view.vegetarian_textView.setTextColor((ContextCompat.getColor(requireContext(), R.color.green)))
        }

        // Vegan Checkmarks
        if(myBundle?.vegan == true) {
            view.vegan_imageView.setColorFilter((ContextCompat.getColor(requireContext(), R.color.green)))
            view.vegan_textView.setTextColor((ContextCompat.getColor(requireContext(), R.color.green)))
        }

        // Gluten Free Checkmarks
        if(myBundle?.glutenFree == true) {
            view.glutenFree_imageView.setColorFilter((ContextCompat.getColor(requireContext(), R.color.green)))
            view.glutenFree_textView.setTextColor((ContextCompat.getColor(requireContext(), R.color.green)))
        }

        // Dairy Free Checkmarks
        if(myBundle?.dairyFree == true) {
            view.dairyFree_imageView.setColorFilter((ContextCompat.getColor(requireContext(), R.color.green)))
            view.dairyFree_textView.setTextColor((ContextCompat.getColor(requireContext(), R.color.green)))
        }


        // Healthy Checkmarks
        if(myBundle?.veryHealthy == true) {
            view.healty_imageView.setColorFilter((ContextCompat.getColor(requireContext(), R.color.green)))
            view.healthy_textView.setTextColor((ContextCompat.getColor(requireContext(), R.color.green)))
        }

        // Cheap Checkmarks
        if(myBundle?.cheap == true) {
            view.cheap_imageView.setColorFilter((ContextCompat.getColor(requireContext(), R.color.green)))
            view.cheap_textView.setTextColor((ContextCompat.getColor(requireContext(), R.color.green)))
        }

        return view
    }
}