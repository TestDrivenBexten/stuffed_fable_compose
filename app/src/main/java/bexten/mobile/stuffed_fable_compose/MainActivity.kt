package bexten.mobile.stuffed_fable_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import bexten.mobile.stuffed_fable_compose.ui.theme.Stuffed_fable_composeTheme

fun getDieUIColor(dieColor: DieColor): Color {
    return when (dieColor) {
        DieColor.BLACK -> Color.Black
        DieColor.WHITE -> Color.White
        DieColor.RED -> Color.Red
        DieColor.GREEN -> Color.Green
        DieColor.YELLOW -> Color.Yellow
        DieColor.BLUE -> Color.Blue
        DieColor.PURPLE -> Color.Magenta
    }
}

var diceBag = DiceBag(
    diceSelectionList = listOf(
        DiceSelection(blackDie, 0, 0, 5),
        DiceSelection(whiteDie, 0, 0, 5),
        DiceSelection(redDie, 0, 0, 5),
        DiceSelection(greenDie, 0, 0, 5),
        DiceSelection(yellowDie, 0, 0, 5),
        DiceSelection(blueDie, 0, 0, 5),
        DiceSelection(purpleDie, 0, 0, 5),
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Stuffed_fable_composeTheme {
                StuffedFablesApp()
            }
        }
    }
}

@Composable
fun StuffedFablesHeader() {
    Text(text = "Stuffed Fables Dice Draw Calculator")
}

@Composable
fun StuffedFablesApp() {
    var diceBag by remember { mutableStateOf(diceBag) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val titles = listOf("Dice Selected", "Dice To Draw")
    val updateDiceSelectionCount: (Int, Int) -> Unit = { x, y ->
        val newDiceBag = updateDiceSelectionDiceCount(diceBag, x, y).getOrNull()
        if (newDiceBag != null) {
            diceBag = newDiceBag
        }
    }
    val updateDrawCountCount: (Int, Int) -> Unit = { x, y ->
        val newDiceBag = updateDiceSelectionToDrawCount(diceBag, x, y).getOrNull()
        if (newDiceBag != null) {
            diceBag = newDiceBag
        }
    }

    Column {
        StuffedFablesHeader()
        TabRow(selectedTabIndex = selectedTab) {
            titles.forEachIndexed { index, title ->
                Tab(text = { Text(title) }, selected = selectedTab == index, onClick = { selectedTab = index })
            }
        }
        when(selectedTab) {
            0 -> DiceSelectionPanel(diceBag, updateDiceSelectionCount)
            1 -> DiceDrawPanel(diceBag, updateDrawCountCount)
        }
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
fun StuffedFablesAppPreview() {
    Stuffed_fable_composeTheme {
        StuffedFablesApp()
    }
}
