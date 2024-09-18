package bexten.mobile.stuffed_fable_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bexten.mobile.stuffed_fable_compose.ui.theme.Stuffed_fable_composeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Stuffed_fable_composeTheme {
                StuffedFablesHeader()
            }
        }
    }
}

@Composable
fun StuffedFablesHeader() {
    Text(text = "Stuffed Fables Dice Draw Calculator")
}

@Composable
fun DieButton(dieOrdinal: Int) {
    Button(onClick = {}, shape = RoundedCornerShape(4.dp)) {
        Text(text = dieOrdinal.toString())
    }
}

@Composable
fun DiceSelection(diceSelected: Int) {
    LazyRow {
        items(diceSelected) { dice -> DieButton(dice + 1) }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionPreview() {
    Stuffed_fable_composeTheme {
        DiceSelection(3)
    }
}

@Preview(showBackground = true)
@Composable
fun DieButtonPreview() {
    Stuffed_fable_composeTheme {
        DieButton(3)
    }
}

@Preview(showBackground = true)
@Composable
fun StuffedFablesHeaderPreview() {
    Stuffed_fable_composeTheme {
        StuffedFablesHeader()
    }
}
