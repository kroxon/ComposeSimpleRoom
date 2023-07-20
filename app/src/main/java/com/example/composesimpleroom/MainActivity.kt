package com.example.composesimpleroom

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Delete
import com.example.composesimpleroom.data.Contact
import com.example.composesimpleroom.ui.theme.ComposeSimpleRoomTheme

class MainActivity : ComponentActivity() {
    private val mainVm by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSimpleRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val contacts = mainVm.getContacts().collectAsState(initial = emptyList())
                    contactList(
                        contacts = contacts.value,
                        onDelete = { contact -> mainVm.deleteContact(contact) }
                    )
                }
            }
        }
    }
}

@Composable
fun contactList(contacts: List<Contact>, onDelete: ((Contact) -> Unit)? = null) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        contactLazyColumn(contacts, onDelete)
    }
}

@Composable
fun contactLazyColumn(contacts: List<Contact>, onDelete: ((Contact) -> Unit)? = null) {
    LazyColumn {
        items(items = contacts, key = { it.id }) { contact ->
            contactRow(contact, onDelete)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun contactRow(contact: Contact, onDelete: ((Contact) -> Unit)? = null) {
    val dismissState = rememberDismissState(confirmStateChange = {
        if (it == DismissValue.DismissedToStart)
            onDelete?.invoke(contact)
        true
    })
    SwipeToDismiss(
        state = dismissState,
        background = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 2.dp)
                    .background(Color.Red),
                horizontalArrangement = Arrangement.End
            ) {
                val imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete)
                Icon(imageVector = imageVector, contentDescription = null)
            }
        },
        dismissThresholds = { FractionalThreshold(0.5f) },
        directions = setOf(DismissDirection.EndToStart)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp).height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${contact.name} ${contact.surname}", fontSize = 18.sp)
                Text(text = "${contact.number}", fontStyle = FontStyle.Italic)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun contactRowPreview() {
    contactRow(contact = contact1)
}

