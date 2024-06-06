package com.mubin.starwars.ui.planets

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mubin.starwars.R
import com.mubin.starwars.base.theme.StarWarsTheme
import com.mubin.starwars.base.utils.Constants.PLANETS
import com.mubin.starwars.base.utils.executeBodyOrReturnNullSuspended
import com.mubin.starwars.base.utils.logThis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PlanetsFragment : Fragment() {

    private val vm by viewModels<PlanetsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setContent {

                StarWarsTheme {
                    val scope = rememberCoroutineScope()
                    if (vm.uiState.response == null) {
                        LaunchedEffect(
                            key1 = "PlanetsFragment",
                            block = {
                                scope.launch {
                                    executeBodyOrReturnNullSuspended {
                                        vm.uiState.isLoading = true
                                        vm.uiState.showRootLayout = false

                                        val result = vm.getPlanets(PLANETS)
                                        if (result == null) {
                                            //handle error
                                        } else {
                                            vm.uiState.response = result
                                            result.results?.forEach{ planet ->
                                                vm.uiState.planetList.add(planet)
                                            }

                                            vm.uiState.isLoading = false
                                            vm.uiState.showRootLayout = true
                                        }
                                    }

                                }

                            }
                        )
                    }

                    InitView(vm.uiState)

                }
            }

        }
    }

    @Composable
    private fun InitView(uiState: PlanetsUIState) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (uiState.isLoading) {

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 5.dp
                )

            } else {
                if (uiState.showRootLayout) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.background
                            )
                            .fillMaxSize()
                    ) {

                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primary
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                        ) {

                            Text(
                                text = "${uiState.response?.count} Planets Found",
                                fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )

                        }

                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxSize()
                        ) {

                            item {
                                Spacer(
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                )
                            }

                            items(uiState.planetList.size) { position ->

                                Card(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .height(IntrinsicSize.Min)
                                        .fillParentMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.tertiary
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
                                                .weight(1f)
                                                .fillMaxHeight()
                                        ) {

                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                text = "MMMMMMMMM",
                                                textAlign = TextAlign.End
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                text = "MMMMMMMMM",
                                                textAlign = TextAlign.End
                                            )

                                        }

                                        VerticalDivider(
                                            modifier = Modifier
                                                .padding(horizontal = 16.dp)
                                                .fillMaxHeight(),
                                            thickness = 2.dp,
                                            color = Color.Black
                                        )

                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxHeight()
                                        ) {

                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                text = "MMMMMMMMM",
                                                textAlign = TextAlign.Start
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                text = "MMMMMMMMM",
                                                textAlign = TextAlign.Start
                                            )

                                        }

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


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {

            //InitView(vm.uiState)

        }
    }


}