package bexten.mobile.stuffed_fable_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun DieButton(dieOrdinal: Int, buttonColor: Color) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text(text = dieOrdinal.toString())
    }
}

@Composable
fun DiceSelectionPanelRow(diceSelected: Int) {
    LazyRow {
        items(diceSelected) { dice -> DieButton(dice + 1, Color.Black) }
    }
}

@Composable
fun DiceSelectionPanel() {
    var isExpanded by remember { mutableStateOf(true) }
    Column {
        Text(text = "Dice Drawn", modifier = Modifier.clickable { isExpanded = !isExpanded })
        if (isExpanded) {
            DiceSelectionPanelRow(5)
            DiceSelectionPanelRow(5)
            DiceSelectionPanelRow(5)
            DiceSelectionPanelRow(5)
            DiceSelectionPanelRow(5)
            DiceSelectionPanelRow(5)
            DiceSelectionPanelRow(5)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionRowPreview() {
    Stuffed_fable_composeTheme {
        DiceSelectionPanelRow(3)
    }
}

@Preview(showBackground = true)
@Composable
fun DieButtonPreview() {
    Stuffed_fable_composeTheme {
        DieButton(3, Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun StuffedFablesHeaderPreview() {
    Stuffed_fable_composeTheme {
        StuffedFablesHeader()
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionPanelPreview() {
    Stuffed_fable_composeTheme {
        DiceSelectionPanel()
    }
}
