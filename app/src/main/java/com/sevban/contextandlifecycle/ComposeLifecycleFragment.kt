package com.sevban.contextandlifecycle


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.sevban.contextandlifecycle.databinding.FragmentComposeLifecycleBinding

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

        val timerState =  mutableStateOf(0)

        binding.composeView.apply {
            setContent {
                MaterialTheme {
/*                    SideEffect {
                        timerState.value++
                    }*/


                    timerState.value++

/*                    LaunchedEffect(key1 = timerState) {
                        while (true) {
                            delay(1000)
                            timerState.value++
                        }
                    }*/

/*                    val animatable = remember { Animatable(0f) }
                    LaunchedEffect(key1 = true) {
                        animatable.animateTo(1f, animationSpec = tween(durationMillis = 5_000))
                    }*/

                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    DisposableEffectWithLifecycle(
                        onResume = {
                            Log.i(TAG, "onResume in Compose View")
                        },
                        onCreate = {
                            Log.i(TAG, "onCreate in Compose View")
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

                    var showDialog by remember { mutableStateOf(false) }
                    if (showDialog)
                        Dialog(
                            onDismissRequest = { showDialog = !showDialog },
                        ) {
                            Surface {
                                Column (
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(
                                        32.dp,
                                        Alignment.CenterVertically
                                    ),
                                    horizontalAlignment = CenterHorizontally
                                ) {
                                    Text(text = "Sample compose dialog text")
                                    Button(onClick = { showDialog = !showDialog }) {
                                        Text(text = "OK")
                                    }
                                }
                            }
                        }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(
                            32.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = CenterHorizontally
                    ) {

/*                        item {
                            Text(text = animatable.value.toString())
                        }*/
                        item {
                            Button(onClick = { showDialog = !showDialog }) {
                                Text(text = "Show dialog")
                            }
                        }
                        item {
                            //Text gösterilmiyorken de side effect oluyor.
                            //Yani text bu state'i okumasın ama yine de sayımız artıyor
                            //ancak recomposition sayısı güncellenmiyor bu sırada.
                            Text("Current time: ${timerState.value}")
                            Button(onClick = {
                                timerState.value++
                            }) {
                                Text("Increment timer")
                            }
                        }
                        items(5) {
                            Text(text = "This is Compose View", fontWeight = FontWeight.Bold)
                        }
                    }
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

@Composable
fun DisposableEffectWithLifecycle(
    onCreate: () -> Unit = {},
    onStart: () -> Unit = {},
    onStop: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onDestroy: () -> Unit = {},
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val currentOnCreate by rememberUpdatedState(onCreate)//rememberUpdatedState is used to lambdas given not to
                                                         //be restarted across recompositions.
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)
    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnPause by rememberUpdatedState(onPause)
    val currentOnDestroy by rememberUpdatedState(onDestroy)

    DisposableEffect(lifecycleOwner) {
        val lifecycleEventObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> currentOnCreate()
                Lifecycle.Event.ON_START -> currentOnStart()
                Lifecycle.Event.ON_PAUSE -> currentOnPause()
                Lifecycle.Event.ON_RESUME -> currentOnResume()
                Lifecycle.Event.ON_STOP -> currentOnStop()
                Lifecycle.Event.ON_DESTROY -> currentOnDestroy()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
        }
    }
}
