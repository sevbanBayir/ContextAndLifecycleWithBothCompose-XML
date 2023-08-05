package com.sevban.contextandlifecycle.presentation.compose_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sevban.contextandlifecycle.databinding.FragmentComposeLifecycleBinding
import com.sevban.contextandlifecycle.ui.theme.ContextAndLifecycleTheme

private val TAG = "ComposeLifecycleFragment"

class ComposeLifecycleFragment : Fragment() {
    private var _binding: FragmentComposeLifecycleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComposeLifecycleBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.i(TAG, "onCreateView in Compose Fragment")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated in Compose Fragment")
        setViews()
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart in Compose Fragment")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume in Compose Fragment")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy in Compose Fragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause in Compose Fragment")
    }

    private fun setViews() {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ContextAndLifecycleTheme {
                    ComposeLifecycleUI(
                        navigate = {
                            val action = ComposeLifecycleFragmentDirections.actionComposeLifecycleFragmentToCameraFragment()
                            findNavController().navigate(action)
                        }
                    )
                }
            }
        }
    }
}