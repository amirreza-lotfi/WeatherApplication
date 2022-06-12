package com.amirreza.presentation.weatherapplication.CityFragment

import android.animation.Animator
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.amirreza.weatherapplication.databinding.FragmentCityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CityFragmentXX : Fragment() {

    private lateinit var binding: FragmentCityBinding
    private val cityFragmentViewModel:CityFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loadingScreen.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                Log.i("", "")
            }
            override fun onAnimationEnd(animator: Animator) {
                binding.loadingScreen.visibility = GONE
                if(cityFragmentViewModel.hasNotAnyCityInDatabase()){
                    //todo navigate to search fragment
                }else{
                    //todo fill content of screen
                }
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

}