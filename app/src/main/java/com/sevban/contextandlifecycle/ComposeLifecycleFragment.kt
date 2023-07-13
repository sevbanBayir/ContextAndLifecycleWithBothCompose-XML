package com.sevban.contextandlifecycle


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.sevban.contextandlifecycle.databinding.FragmentComposeLifecycleBinding
import com.sevban.contextandlifecycle.ui.theme.ContextAndLifecycleTheme
import com.sevban.contextandlifecycle.components.ComposeDialog
import com.sevban.contextandlifecycle.components.DisposableEffectWithLifecycle
import com.sevban.contextandlifecycle.components.LazyColumnWithAnimation

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

        bindViews()
    }

    private fun bindViews() {
        binding.composeView.apply {
            setContent {
                ContextAndLifecycleTheme {
                    var showDialog by remember { mutableStateOf(false) }
                    val timerState = remember { mutableStateOf(0) }
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    DisposableEffectWithLifecycle(
                        onResume = {
                            Log.i(TAG, "onResume in Compose View")
                        },
                        onCreate = {
                            Log.i(TAG, "onCreate in  Compose View")
                        },
                        onDestroy = {
                            Log.i(TAG, "onDestroy in Compose View")
                        },
                        onPause = {
                            Log.i(TAG, "onPause in Compose View")
                        },
                        onStart = {
                            Log.i(TAG, "onStart in Compose View")
                        },
                        onStop = {
                            Log.i(TAG, "onStop in Compose View")
                        }
                    )

                    if (showDialog)
                        ComposeDialog(
                            onDismiss = { showDialog = !showDialog },
                            onClickOKButton = { showDialog = !showDialog }
                        )

                    /**This is a side-effect because when user clicks the button below to increment
                    the value, all places that reads this state will be updated. Since here we
                    read that state here it also fires off this line and once this line is fired off
                    it causes an infinite recomposition. This can be simply explained by that this line
                    knows nothing about the lifecycle of that composable.*/

                    timerState.value++
                    LazyColumnWithAnimation(
                        timerState = timerState.value,
                        onClickShowDialogButton = {//b&w background, rounded corner 6-8dp, //leadingIcon
                            showDialog = !showDialog
                        },
                        onClickIncrementCountButton = { timerState.value++ }
                    )
                }
            }
        }
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
        _binding = null
        Log.i(TAG, "onDestroy in Compose Fragment")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause in Compose Fragment")
    }
}


