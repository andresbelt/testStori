package com.stori.interviewtest.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stori.interviewtest.data.models.Movement
import com.stori.interviewtest.presentation.R
import com.stori.interviewtest.presentation.R.string
import com.stori.interviewtest.ui.components.MovementListItem
import com.stori.interviewtest.ui.components.ProgressBar
import com.stori.interviewtest.ui.components.TopBar
import com.stori.interviewtest.ui.screens.home.HomeScreenViewEvent.Init
import com.stori.interviewtest.ui.screens.home.HomeScreenViewEvent.SignOut
import com.stori.interviewtest.ui.screens.home.HomeScreenViewState.ListDisplay
import com.stori.interviewtest.ui.screens.home.HomeScreenViewState.Loading
import com.stori.interviewtest.ui.screens.home.HomeScreenViewState.ProfileUpdate
import com.stori.interviewtest.ui.screens.signin.components.SignInTopBar

@Composable
@ExperimentalComposeUiApi
fun HomeProfile(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(string.app_name),
                signOut = {
                    viewModel.postEvent(SignOut)
                })
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                val viewState by viewModel.viewState.collectAsState()
                val listMovements by viewModel.listMovements.collectAsState()
                val profile by viewModel.profile.collectAsState()

                LaunchedEffect(
                    key1 = viewModel,
                    block = { viewModel.postEvent(Init) }
                )
                ProfileContent( profile)
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxSize()
                ) {
                    favourite(listMovements)
                }

                when (val state = viewState) {
                    Loading -> ProgressBar()
                    is ListDisplay -> {
                        LazyRow(
                            contentPadding = PaddingValues(all = 16.dp)
                        )
                        {
                            items(state.movements.size) { index ->
                                MovementListItem(modifier = Modifier, itemBet = state.movements[index])
                            }
                        }
                    }
                    is ProfileUpdate -> {
                    }
                }
            }
        }
    )
}

fun LazyListScope.favourite(movements: List<Movement?>?) {
            movements?.let {
                if (movements.isEmpty()) {
                    item { Text(text = stringResource(id = R.string.no_movements)) }
                }
                items(movements.size) { index ->
                    val info = movements[index]
                    info?.let {
                        MovementListItem(modifier = Modifier, itemBet = it)
                    }
                }
            }
}


