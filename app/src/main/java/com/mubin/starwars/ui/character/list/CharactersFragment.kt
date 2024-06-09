package com.mubin.starwars.ui.character.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mubin.starwars.R
import com.mubin.starwars.base.theme.StarWarsTheme
import com.mubin.starwars.base.utils.Constants
import com.mubin.starwars.base.utils.Constants.CHARACTERS
import com.mubin.starwars.base.utils.CustomAlertDialog
import com.mubin.starwars.base.utils.InfiniteListHandler
import com.mubin.starwars.base.utils.executeBodyOrReturnNullSuspended
import com.mubin.starwars.ui.planets.PlanetsUIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment(){

    private val vm by viewModels<CharacterListViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                StarWarsTheme {

                    val shouldShowDialog = remember { mutableStateOf(false) }
                    val scope = rememberCoroutineScope()
                    val listViewState = rememberLazyListState()

                    CustomAlertDialog(
                        shouldShowDialog = shouldShowDialog,
                        title = "Error",
                        text = "Ops! Something bad happened.",
                        positiveButtonTitle = "Okay"
                    )

                    if (vm.uiState.response == null) {
                        LaunchedEffect(
                            key1 = "PlanetsFragment",
                            block = {
                                scope.launch {
                                    executeBodyOrReturnNullSuspended {
                                        vm.uiState.isLoading = true
                                        vm.uiState.showRootLayout = false

                                        val result = vm.getCharacters(CHARACTERS)
                                        if (result == null) {
                                            shouldShowDialog.value = true
                                        } else {
                                            vm.uiState.response = result
                                            vm.uiState.totalCount = result.count ?: 0
                                            vm.uiState.nextPage = result.next ?: ""
                                            result.results?.forEach{ planet ->
                                                vm.uiState.characterList.add(planet)
                                            }
                                            vm.uiState.showRootLayout = true
                                        }

                                        vm.uiState.isLoading = false
                                    }

                                }

                            }
                        )
                    }

                    if (vm.uiState.characterList.isNotEmpty()) {
                        InfiniteListHandler(listState = listViewState, buffer = 2) {
                            scope.launch {
                                executeBodyOrReturnNullSuspended {
                                    if (vm.uiState.nextPage.isNotEmpty()) {
                                        vm.uiState.isMoreLoading = true
                                        val result = vm.getCharacters(vm.uiState.nextPage)
                                        if (result == null) {
                                            //handle error
                                        } else {
                                            vm.uiState.response = result
                                            vm.uiState.nextPage = result.next ?: ""
                                            result.results?.forEach { planet ->
                                                vm.uiState.characterList.add(planet)
                                            }
                                        }

                                        vm.uiState.isMoreLoading = false
                                    }
                                }

                            }

                        }
                    }

                    InitView(vm.uiState, listViewState)

                }

            }

        }
    }

    @Composable
    private fun InitView(uiState: CharactersUIState, listViewState: LazyListState) {

        Scaffold(
            modifier = Modifier,
            topBar = {
                if (!uiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    ) {

                        Text(
                            text = "${uiState.response?.count ?: 0} Planets Found",
                            fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )

                    }
                }
            }
        ) {


            if (uiState.isLoading) {

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(it)
                            .size(40.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 4.dp
                    )

                }

            } else {
                if (uiState.showRootLayout) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(it)
                            .padding(horizontal = 16.dp)
                            .fillMaxSize(),
                        state = listViewState
                    ) {

                        item {
                            Spacer(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                            )
                        }

                        items(uiState.characterList.size) { position ->

                            val planetData = uiState.characterList[position]

                            Card(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .height(IntrinsicSize.Min)
                                    .fillParentMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline
                                ),
                                elevation = CardDefaults.cardElevation(5.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        .padding(16.dp)
                                        .fillMaxHeight(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .weight(5f)
                                            .fillMaxHeight()
                                    ) {

                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            text = "Name:",
                                            fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.secondary,
                                            textAlign = TextAlign.End
                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(top = 4.dp)
                                                .fillMaxWidth(),
                                            text = "Gender:",
                                            fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.secondary,
                                            textAlign = TextAlign.End
                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(top = 4.dp)
                                                .fillMaxWidth(),
                                            text = "Year of Birth:",
                                            fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.secondary,
                                            textAlign = TextAlign.End
                                        )

                                    }

                                    VerticalDivider(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .fillMaxHeight(),
                                        thickness = 2.dp,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )

                                    Column(
                                        modifier = Modifier
                                            .weight(6f)
                                            .fillMaxHeight()
                                    ) {

                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            text = planetData?.name ?: "",
                                            fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                                            fontSize = 16.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            textAlign = TextAlign.Start
                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(top = 4.dp)
                                                .fillMaxWidth(),
                                            text = planetData?.gender ?: "",
                                            fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                                            fontSize = 16.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            textAlign = TextAlign.Start
                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(top = 4.dp)
                                                .fillMaxWidth(),
                                            text = planetData?.birthYear ?: "",
                                            fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                                            fontSize = 16.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            textAlign = TextAlign.Start
                                        )

                                    }

                                }
                            }

                        }

                        if (uiState.isMoreLoading) {

                            item {

                                Box(
                                    modifier = Modifier
                                        .padding(top = 16.dp, bottom = 24.dp)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {

                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(40.dp),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        strokeWidth = 4.dp
                                    )

                                }
                            }

                        }

                        item {
                            Spacer(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                            )
                        }

                    }
                }
            }

        }

    }

}