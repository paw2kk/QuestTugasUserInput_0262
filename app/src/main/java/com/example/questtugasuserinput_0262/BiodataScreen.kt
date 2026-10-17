package com.example.questtugasuserinput_0262


import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DimenRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
private fun spResource(@DimenRes id: Int): TextUnit =
    with(LocalDensity.current) { dimensionResource(id).toSp() }


@Composable
fun BiodataScreen() {
    // ---------- State ----------
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    val selectedSkills = remember { mutableStateListOf<String>() }

    val isFormValid = firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            birthDate.isNotBlank() &&
            experience.isNotBlank() &&
            selectedSkills.isNotEmpty()
    Scaffold(
        containerColor = colorResource(R.color.scaffold_background)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            // Gambar header di belakang
            Image(
                painter = painterResource(R.drawable.bg_kucing_meledak),
                contentDescription = stringResource(R.string.cd_header_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.header_height))
            )

            // Indikator titik-titik
            DotsIndicator(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = dimensionResource(R.dimen.dots_padding_start),
                        top = dimensionResource(R.dimen.dots_padding_top)
                    )
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.box_bottom_margin))
                    .height(dimensionResource(R.dimen.box_height_offset)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.box_corner)),
                color = colorResource(R.color.white),
                shadowElevation = dimensionResource(R.dimen.box_elevation)
            ){
                FormContent(
                    firstName = firstName, onFirstName = { firstName = it },
                    lastName = lastName, onLastName = { lastName = it },
                    email = email, onEmail = { email = it },
                    birthDate = birthDate, onBirthDate = { birthDate = it },
                    experience = experience, onExperience = { experience = it },
                    selectedSkills = selectedSkills,
                    isFormValid = isFormValid
                )
            }
        }
    }
}

@Composable
private fun FormContent(
    firstName: String, onFirstName: (String) -> Unit,
    lastName: String, onLastName: (String) -> Unit,
    email: String, onEmail: (String) -> Unit,
    birthDate: String, onBirthDate: (String) -> Unit,
    experience: String, onExperience: (String) -> Unit,
    selectedSkills: MutableList<String>,
    isFormValid: Boolean
) {
    val fieldSpacing = dimensionResource(R.dimen.field_spacing)
    val skills = stringArrayResource(R.array.skill_list)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = dimensionResource(R.dimen.box_padding_horizontal),
                vertical = dimensionResource(R.dimen.box_padding_vertical)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Judul
        Text(
            text = stringResource(R.string.title_form),
            color = colorResource(R.color.purple_primary),
            fontSize = spResource(R.dimen.text_title),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.space_small)))
        Text(
            text = stringResource(R.string.subtitle_form),
            color = colorResource(R.color.text_secondary),
            fontSize = spResource(R.dimen.text_subtitle),
            letterSpacing = spResource(R.dimen.letter_spacing_subtitle),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.space_xlarge)))

        // Nama depan & belakang
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small))
        ) {
            BiodataField(
                value = firstName,
                onValueChange = onFirstName,
                hint = stringResource(R.string.hint_first_name),
                modifier = Modifier.weight(1f)
            )
            BiodataField(
                value = lastName,
                onValueChange = onLastName,
                hint = stringResource(R.string.hint_last_name),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(fieldSpacing))

        // Email
        BiodataField(
            value = email,
            onValueChange = onEmail,
            hint = stringResource(R.string.hint_email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(fieldSpacing))

        // Tgl lahir & experience
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small))
        ) {
            BirthDateField(
                value = birthDate,
                onDateSelected = onBirthDate,
                modifier = Modifier.weight(1f)
            )
            ExperienceDropdown(
                value = experience,
                onSelected = onExperience,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(fieldSpacing))

        // Skill
        Text(
            text = stringResource(R.string.label_skill),
            color = colorResource(R.color.text_primary),
            fontSize = spResource(R.dimen.text_label),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.space_small)))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            skills.forEach { skill ->
                val checked = skill in selectedSkills
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { isChecked ->
                            if (isChecked) selectedSkills.add(skill) else selectedSkills.remove(skill)
                        }
                    )
                    Text(
                        text = skill,
                        color = colorResource(R.color.text_primary),
                        fontSize = spResource(R.dimen.text_checkbox)
                    )
                }
            }
        }

        Spacer(Modifier.height(dimensionResource(R.dimen.space_large)))

        // Tombol proceed
        Button(
            onClick = { /* TODO: proses data */ },
            enabled = isFormValid,
            shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.purple_primary),
                contentColor = colorResource(R.color.white),
                disabledContainerColor = colorResource(R.color.button_disabled),
                disabledContentColor = colorResource(R.color.button_disabled_text)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_height))
        ) {
            Text(
                text = stringResource(R.string.btn_proceed),
                fontSize = spResource(R.dimen.text_button),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ---------- Komponen reusable ----------

@Composable
private fun biodataFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = colorResource(R.color.field_border_focused),
    unfocusedBorderColor = colorResource(R.color.field_border),
    focusedTextColor = colorResource(R.color.text_primary),
    unfocusedTextColor = colorResource(R.color.text_primary),
    focusedPlaceholderColor = colorResource(R.color.text_primary),
    unfocusedPlaceholderColor = colorResource(R.color.text_primary),
    cursorColor = colorResource(R.color.purple_primary)
)

@Composable
private fun BiodataField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint, fontSize = spResource(R.dimen.text_field)) },
        textStyle = TextStyle(fontSize = spResource(R.dimen.text_field)),
        singleLine = true,
        shape = RoundedCornerShape(dimensionResource(R.dimen.field_corner)),
        colors = biodataFieldColors(),
        modifier = modifier.height(dimensionResource(R.dimen.field_height))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BirthDateField(
    value: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val pattern = stringResource(R.string.date_pattern)

    Box(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.hint_birth_date),
                    fontSize = spResource(R.dimen.text_field)
                )
            },
            textStyle = TextStyle(fontSize = spResource(R.dimen.text_field)),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.cd_pick_date),
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(dimensionResource(R.dimen.field_corner)),
            colors = biodataFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.field_height))
        )
        // Overlay agar seluruh field bisa diklik
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(dimensionResource(R.dimen.field_corner)))
                .clickable { showDialog = true }
        )
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
                            .apply { timeZone = TimeZone.getTimeZone("UTC") }
                        onDateSelected(formatter.format(Date(millis)))
                    }
                    showDialog = false
                }) { Text(stringResource(R.string.dialog_ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExperienceDropdown(
    value: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val options = stringArrayResource(R.array.experience_list)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.hint_experience),
                    fontSize = spResource(R.dimen.text_field)
                )
            },
            textStyle = TextStyle(fontSize = spResource(R.dimen.text_field)),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            singleLine = true,
            shape = RoundedCornerShape(dimensionResource(R.dimen.field_corner)),
            colors = biodataFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.field_height))
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

/** Jumlah & warna dot dibaca dari array R.array.dot_colors (colors.xml). */
@Composable
private fun DotsIndicator(modifier: Modifier = Modifier) {
    val resources = LocalContext.current.resources
    val dotColors = remember {
        val array = resources.obtainTypedArray(R.array.dot_colors)
        val colors = List(array.length()) {
            Color(array.getColor(it, Color.Transparent.toArgb()))
        }
        array.recycle()
        colors
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dot_spacing))
    ) {
        dotColors.forEach { dotColor ->
            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.dot_size))
                    .background(color = dotColor, shape = CircleShape)
            )
        }
    }
}
