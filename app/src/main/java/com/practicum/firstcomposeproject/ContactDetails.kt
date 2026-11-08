package com.practicum.firstcomposeproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetails(contact: Contact) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "FirstComposeProject", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ContactAvatar(contact)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val fullName = "${contact.name} ${contact.surname ?: ""}".trim()

                    Text(
                        text = fullName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = contact.familyName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Normal
                        )
                        if (contact.isFavorite) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = android.R.drawable.star_big_on),
                                contentDescription = "Избранный контакт",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoRow(label = stringResource(id = R.string.phone), value = contact.phone)
                InfoRow(label = stringResource(id = R.string.address), value = contact.address)
                contact.email?.let {
                    InfoRow(label = stringResource(id = R.string.email), value = it)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ContactAvatar(contact: Contact) {
    if (contact.imageRes != null) {
        Image(
            painter = painterResource(id = contact.imageRes),
            contentDescription = "Фото контакта",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            val initials = "${contact.name.take(1)}${contact.familyName.take(1)}"
            Text(
                text = initials,
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$label: ",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            color = Color.Black,
            textAlign = TextAlign.End
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(showBackground = true, name = "ProfileWithoutPhotoPreview - portrait")
@Composable
fun ProfileWithoutPhotoPreview() {
    MaterialTheme {
        ContactDetails(
            contact = Contact(
                name = "Евгений",
                surname = "Андреевич",
                familyName = "Лукашин",
                imageRes = null,
                isFavorite = true,
                phone = "+7 495 495 95 95",
                address = "г. Москва, 3-я улица Строителей, д. 25, кв. 12",
                email = "ELukashin@practicum.ru"
            )
        )
    }
}

@Preview(showBackground = true, name = "ProfileWithPhotoPreview - portrait")
@Composable
fun ProfileWithPhotoPreview() {
    MaterialTheme {
        ContactDetails(
            contact = Contact(
                name = "Василий",
                surname = null,
                familyName = "Кузякин",
                imageRes = android.R.drawable.ic_menu_camera,
                isFavorite = false,
                phone = "---",
                address = "Ивановская область, дер. Крутово, д. 4",
                email = null
            )
        )
    }
}