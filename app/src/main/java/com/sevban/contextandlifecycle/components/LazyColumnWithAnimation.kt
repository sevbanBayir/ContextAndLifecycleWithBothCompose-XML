package com.sevban.contextandlifecycle.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sevban.contextandlifecycle.R
import com.sevban.contextandlifecycle.ui.theme.Montserrat
import com.sevban.contextandlifecycle.ui.theme.dimesions
import com.sevban.contextandlifecycle.ui.theme.textSize

@Composable
fun LazyColumnWithAnimation(
    timerState: Int,
    onClickShowDialogButton: () -> Unit,
    onClickIncrementCountButton: () -> Unit
) {
    LazyColumn(//itemplacementanim & itemspecific anim for the first & + swipe refresh

        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            MaterialTheme.dimesions.genericComponentSpacing,
            Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Button(onClick = onClickShowDialogButton) {
                Text(text = "Show dialog")
            }
        }

        item {
            //Text gösterilmiyorken de side effect oluyor.
            //Yani text bu state'i okumasın ama yine de sayımız artıyor
            //ancak recomposition sayısı güncellenmiyor bu sırada.
            Text("Current time: ${timerState}")
            RoundedBlackAndWhiteButton(
                onClick = onClickIncrementCountButton,
                textResId = R.string.increment_counter,
                contentDescription = R.string.increment_counter_button_cd
            )
        }
        //
        items(5) {
            Text(
                text = "This is Compose View",
                fontFamily = Montserrat,
                fontSize = MaterialTheme.textSize.genericFontSize,
            )//font-family roboto as default & 14sp default fontsize global
        }
        //accessiblity appliance
    }
}