package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import consts.Colors

@Composable
fun ErrorView(
    throwable: Throwable
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                Colors.field,
                RoundedCornerShape(10)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Error",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Divider(
            modifier = Modifier
                .padding(vertical = 14.dp)
        )
        Text(
            text = throwable.message ?: "Unknown error",
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.5f)
                .padding(horizontal = 10.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(10.dp))
    }
}