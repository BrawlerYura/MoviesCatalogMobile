package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun Menu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onEditRequested: () -> Unit,
    onDeleteRequested: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.edit),
                    style = MyTypography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Start)
                )
            },
            onClick = {
                onDismiss()
                onEditRequested()
            },
            trailingIcon = {
                Icon(
                    painterResource(R.drawable.ic_pen),
                    null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(Color.LightGray))
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.delete),
                    style = MyTypography.bodyLarge,
                    color =  MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Start)
                )
            },
            onClick = {
                onDismiss()
                onDeleteRequested()
            },
            trailingIcon = {
                Icon(
                    painterResource(R.drawable.ic_trash_can),
                    null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuPreview() {
    Menu(
        expanded = true,
        onDismiss = { },
        onDeleteRequested = { },
        onEditRequested = { }
    )
}