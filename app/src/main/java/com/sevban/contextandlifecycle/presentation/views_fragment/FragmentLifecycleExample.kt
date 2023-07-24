package com.sevban.contextandlifecycle.presentation.views_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sevban.contextandlifecycle.databinding.FragmentLifecycleExampleBinding

class FragmentLifecycleExample : Fragment() {
    private var _binding: FragmentLifecycleExampleBinding? = null
    private val binding get() = _binding!!
    private val TAG = "FragmentLifeCycleExample"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLifecycleExampleBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        Log.i(TAG, "onViewCreated")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    private fun setViews() {
        binding.navigateToComposeScreen.setOnClickListener {
            val action =
                FragmentLifecycleExampleDirections.actionFragmentLifecycleExampleToComposeLifecycleFragment()
            findNavController().navigate(action)
        }

        binding.openDialogBtn.setOnClickListener {
            DialogFragmentExample().show(parentFragmentManager, DialogFragmentExample.TAG)
        }

    }
}