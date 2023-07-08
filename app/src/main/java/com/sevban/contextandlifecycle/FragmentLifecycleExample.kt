package com.sevban.contextandlifecycle

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
        val view = binding.root

        Log.i(TAG, "onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigateToComposeScreen.setOnClickListener {
            val action = FragmentLifecycleExampleDirections.actionFragmentLifecycleExampleToComposeLifecycleFragment()
            findNavController().navigate(action)
        }

        binding.openDialogBtn.setOnClickListener {
            DialogFragmentExample().show(childFragmentManager, DialogFragmentExample.TAG)
        }

        Log.i(TAG, "onViewCreated")
    }
    override fun onDestroy() {
        super.onDestroy()
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
}