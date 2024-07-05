package com.ssajudn.expensetracker.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssajudn.expensetracker.data.local.entities.Saving
import com.ssajudn.expensetracker.presentation.viewmodel.AddSavingsViewModel
import com.ssajudn.expensetracker.presentation.viewmodel.UpdateFieldSaving
import com.ssajudn.expensetracker.util.Utils
import com.ssajudn.expensetracker.util.Utils.formatDateToHumanReadableForm

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SavingsForm(
    modifier: Modifier = Modifier,
    onAddSavingClick: (model: Saving) -> Unit,
    viewModel: AddSavingsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val savingsName = viewModel.getSavingsNames().collectAsState(initial = emptyList())
    val dateDialogVisibility = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.padding(horizontal = 8.dp),
        topBar = {
            TopAppBar(
                title = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Add Savings",
                                style = MaterialTheme.typography.titleSmall
                            )
                        },
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val model = Saving(
                                id = null,
                                title = viewModel.selectedSavingName.value,
                                amount = viewModel.amount.value.toDoubleOrNull() ?: 0.0,
                                date = viewModel.date.value.toString(),
                                goal = viewModel.goal.value
                            )
                            onAddSavingClick(model)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Save",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            FormSection(
                title = "Saving Name",
                content = {
                    ExpenseDropDown(
                        listOfItems = savingsName.value,
                        onItemSelected = {
                            viewModel.updateField(
                                UpdateFieldSaving.SAVING_NAME,
                                it
                            )
                        },
                        initialText = "Select Saving"
                    )
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            FormSection(
                title = "Amount",
                content = {
                    OutlinedTextField(
                        value = viewModel.amount.value,
                        onValueChange = { viewModel.updateField(UpdateFieldSaving.AMOUNT, it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Example: 10000",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                            )
                        },
                    )
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            FormSection(
                title = "Date",
                content = {
                    OutlinedTextField(
                        value = if (viewModel.date.value == 0L) "" else formatDateToHumanReadableForm(
                            viewModel.date.value
                        ),
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { dateDialogVisibility.value = true },
                        placeholder = {
                            Text(
                                text = "Select Date",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                            )
                        },
                        readOnly = true,
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Select Date"
                            )
                        }
                    )

                    if (dateDialogVisibility.value) {
                        ExpenseDatePickerDialog(
                            onDateSelected = {
                                viewModel.updateField(UpdateFieldSaving.DATE, it.toString())
                                dateDialogVisibility.value = false
                            },
                            onDismiss = { dateDialogVisibility.value = false }
                        )
                    }
                }
            )
        }
    }
}

