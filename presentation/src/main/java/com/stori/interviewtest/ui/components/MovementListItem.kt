package com.stori.interviewtest.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stori.interviewtest.data.models.Movement
import com.stori.interviewtest.ui.theme.ColorBorder
import com.stori.interviewtest.ui.theme.ColorPrimary

@Composable
fun MovementListItem(
    modifier: Modifier,
    itemBet: Movement
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(listOf(Color.Transparent, ColorBorder)),
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(12.dp))

    ) {
        Column (
        ){
            Row {
                Text(
                    text = "Movement: ",
                    color = ColorPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
                Text(
                    text = itemBet.type?: "",
                    color = ColorPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
            Row {
                Text(
                    text = "Descripcion: ",
                    color = ColorPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
                Text(
                    text = itemBet.description?: "",
                    color = ColorPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
            Row {
                Text(
                    text = "Total: ",
                    color = ColorPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
                Text(
                    text = itemBet.total.toString(),
                    color = ColorPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun CharacterGridItemPreview() {
    MovementListItem(
        modifier = Modifier.fillMaxWidth(),
        itemBet = Movement("Winning team", "10", "kjsakas"),
    )
}
