package com.amirreza.presentation.weatherapplication.LandingFragment

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amirreza.weatherapplication.R
import com.amirreza.weatherapplication.databinding.FragmentLandingBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LandingFragment : Fragment() {
    lateinit var binding: FragmentLandingBinding
    lateinit var viewModel:LandingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewLanding.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
                viewModel = getViewModel()
            }

            override fun onAnimationEnd(p0: Animator?) {
                viewModel.countOfSavedCity.observe(viewLifecycleOwner){
                    findNavController().navigate(
                        if(it==0) R.id.action_landingFragment_to_searchFragment else R.id.action_landingFragment_to_cityFragment
                    )
                }
            }

            override fun onAnimationCancel(p0: Animator?) {
                TODO("Not yet implemented")
            }
            override fun onAnimationRepeat(p0: Animator?) {
                TODO("Not yet implemented")
            }

        })
    }

}