package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}


//mémorise l'étape et les pressions restantes
@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {

    var etapAct by remember { mutableIntStateOf(1) }
    var presRest by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color (0xFFF9E44C))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Lemonade",
                    fontWeight = FontWeight.Bold
                )
            }

},
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        //l'etape de 1 à 4
        when (etapAct) {
            //etape1: l'utilisateur appuie sur le citronnier
            1 -> LemonTextAndImage(
                textLabelResourceId = R.string.lemon_select,
                drawableResourceId = R.drawable.lemon_tree,
                contentDescriptionResourceId = R.string.lemon_tree_content_description,
                onImageClick = {
                    etapAct = 2
                    presRest = (2..4).random()
                },
                modifier = Modifier.padding(innerPadding)
            )
            //etape2: l'utilisateur presse le citron
            2 -> LemonTextAndImage(
                textLabelResourceId = R.string.lemon_squeeze,
                drawableResourceId = R.drawable.lemon_squeeze,
                contentDescriptionResourceId = R.string.lemon_content_description,
                onImageClick = {
                    etapAct--
                    if (presRest == 0) {
                        etapAct = 3
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )
            //etape3: l'utilisateur boit la citronnade
            3 -> LemonTextAndImage(
                textLabelResourceId = R.string.lemon_drink,
                drawableResourceId = R.drawable.lemon_drink,
                contentDescriptionResourceId = R.string.glass_of_lemonade_content_description,
                onImageClick = { etapAct = 4 },
                modifier = Modifier.padding(innerPadding)
            )
            //etape4: l'utilisateur appuie sur le verre vide
            4 -> LemonTextAndImage(
                textLabelResourceId = R.string.lemon_empty_glass,
                drawableResourceId = R.drawable.lemon_restart,
                contentDescriptionResourceId = R.string.empty_glass_content_description,
                onImageClick = { etapAct = 1 },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}



@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //bouton
        Button (
            onClick = onImageClick,
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC3ECD2))
        ) {
            //image
            Image(painter = painterResource(drawableResourceId),
                contentDescription = stringResource(contentDescriptionResourceId),
                modifier = Modifier
                    .width(128.dp)
                    .height(160.dp)
                    .padding(16.dp)
            )
        }
        //espace de 16dp
        Spacer(modifier = Modifier.height(16.dp))
        //texte affcihé sous l'image
        Text(
            text = stringResource(textLabelResourceId),
            fontSize = 18.sp
        )
    }
}
// aperçu de l'application
@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}