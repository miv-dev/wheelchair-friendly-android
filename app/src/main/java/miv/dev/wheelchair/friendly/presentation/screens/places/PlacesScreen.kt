package miv.dev.wheelchair.friendly.presentation.screens.places

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.layout.DisplayFeature
import coil.compose.AsyncImage
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import miv.dev.wheelchair.friendly.domain.entities.Place
import miv.dev.wheelchair.friendly.getApplicationComponent
import miv.dev.wheelchair.friendly.presentation.components.map.Map
import miv.dev.wheelchair.friendly.presentation.components.nav.AppDockedSearchBar
import miv.dev.wheelchair.friendly.utils.AppContentType
import miv.dev.wheelchair.friendly.utils.AppNavigationType

@Composable
fun PlacesScreen(
	contentType: AppContentType,
	navigationType: AppNavigationType,
	displayFeatures: List<DisplayFeature>
) {
	
	val component = getApplicationComponent()
	val vm: PlacesScreenViewModel = viewModel(factory = component.getViewModelFactory())
	val screenState = vm.screenState.collectAsState(initial = PlaceScreenState.Initial)
	
	
	LaunchedEffect(key1 = Unit) {
		vm.fetchPlaces()
	}
	
	PlacesScreenContent(
		contentType = contentType,
		navigationType = navigationType,
		displayFeatures = displayFeatures,
		viewModel = vm,
		state = screenState
	)
}


@Composable
fun PlacesScreenContent(
	contentType: AppContentType,
	navigationType: AppNavigationType,
	displayFeatures: List<DisplayFeature>,
	viewModel: PlacesScreenViewModel,
	state: State<PlaceScreenState>
) {
	val placeLazyListState = rememberLazyListState()
	var selectedPlace by remember {
		mutableStateOf<Place?>(null)
	}
	if (contentType == AppContentType.DUAL_PANE) {
		TwoPane(
			first = {
				Column(
					verticalArrangement = Arrangement.spacedBy(10.dp),
					modifier = Modifier.padding(vertical = 16.dp)
				) {
					AppDockedSearchBar(
						modifier = Modifier
							.fillMaxWidth()
					
					) {
					
					}
					
					when (val currentState = state.value) {
						PlaceScreenState.Initial -> {
						
						}
						
						PlaceScreenState.Loading -> {
							Box(Modifier.fillMaxSize()) {
								CircularProgressIndicator(Modifier.align(Alignment.Center))
							}
						}
						
						is PlaceScreenState.Places ->
							PlaceList(
								places = currentState.list,
								placeLazyListState = placeLazyListState,
								onPlaceClick = { _ -> })
						
						is PlaceScreenState.Error -> Text(text = currentState.msg)
					}
					
				}
			},
			second = {
				Map(
					modifier = Modifier
						.padding(16.dp)
						.clip(MaterialTheme.shapes.medium),
				)
			},
			strategy = HorizontalTwoPaneStrategy(0.5f, gapWidth = 16.dp),
			displayFeatures = displayFeatures
		)
	} else {
		SinglePaneContent(
			state = state,
			placeLazyListState = placeLazyListState,
			navigateToDetail = { _, _ -> })
	}
	
}

@Composable
fun SinglePaneContent(
	state: State<PlaceScreenState>,
	modifier: Modifier = Modifier,
	placeLazyListState: LazyListState,
	navigateToDetail: (Place, AppContentType) -> Unit
) {
	val theme = MaterialTheme
	var showMap by remember {
		mutableStateOf(false)
	}
	var selectedPlace by remember {
		mutableStateOf<Place?>(null)
	}
	
	Scaffold(
		floatingActionButton = {
			AnimatedVisibility(visible = !showMap) {
				FloatingActionButton(onClick = { /*TODO*/ }) {
					Icon(Icons.Rounded.AddLocationAlt, contentDescription = null)
				}
			}
		}
	) { paddingValues ->
		Column(
			Modifier
				.padding(paddingValues)
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp)
		) {
			AnimatedVisibility(visible = showMap) {
				Column(
					Modifier
						.fillMaxWidth()
						.padding(horizontal = 4.dp),
					horizontalAlignment = Alignment.End,
					verticalArrangement = Arrangement.spacedBy(10.dp)
				) {
					Map(
						Modifier
							.fillMaxHeight(0.5f)
							.clip(theme.shapes.small)
					)
					Row(
						Modifier
							.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
					) {
						FilledTonalIconButton(onClick = {
						}) {
							Icon(Icons.Rounded.Fullscreen, contentDescription = null)
						}
						FilledTonalIconButton(onClick = {
							showMap = false
							selectedPlace = null
						}) {
							Icon(Icons.Rounded.ArrowUpward, contentDescription = null)
						}
					}
				}
			}
			AnimatedVisibility(visible = !showMap) {
				AppDockedSearchBar(
					modifier = Modifier.fillMaxWidth(),
					onActive = {
					
					}
				)
			}
			
			when (val currentState = state.value) {
				PlaceScreenState.Initial -> {
				
				}
				
				PlaceScreenState.Loading -> {
					Box(Modifier.fillMaxSize()) {
						CircularProgressIndicator(Modifier.align(Alignment.Center))
					}
				}
				
				is PlaceScreenState.Places -> {
					
					PlaceList(
						places = currentState.list,
						placeLazyListState = placeLazyListState,
						onPlaceClick = {
							showMap = true
							selectedPlace = it
						},
						selectedPlace = selectedPlace
					)
					
				}
				
				is PlaceScreenState.Error -> Text(text = currentState.msg)
			}
			
			
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceList(
	places: List<Place>,
	modifier: Modifier = Modifier,
	placeLazyListState: LazyListState,
	selectedPlace: Place? = null,
	onPlaceClick: (Place) -> Unit
) {
	val theme = MaterialTheme
	
	
	Box(modifier = modifier) {
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth(),
			state = placeLazyListState,
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(places) {
				Card(modifier = Modifier.fillMaxWidth(), onClick = {
					onPlaceClick(it)
				}) {
					Column(Modifier.padding(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
						Row(
							
							horizontalArrangement = Arrangement.spacedBy(4.dp),
							verticalAlignment = Alignment.CenterVertically
						) {
							Surface(
								modifier = Modifier
									.height(100.dp)
									.aspectRatio(1f / 1f),
								shape = theme.shapes.small,
							) {
								if (it.pictures.isNotEmpty()) {
									AsyncImage(
										model = it.getPictureUrl(it.pictures.first()),
										contentDescription = "Picture",
										contentScale = ContentScale.FillBounds,
										
										)
								} else {
									Box(modifier = Modifier.fillMaxSize()) {
										Icon(
											imageVector = Icons.Rounded.Image,
											contentDescription = null,
											modifier = Modifier
												.align(Alignment.Center)
												.size(40.dp)
										)
									}
								}
							}
							Surface(
								shape = theme.shapes.small,
								modifier = Modifier
									.height(100.dp)
									.weight(1f)
							
							) {
								Column(
									Modifier
										.fillMaxSize(),
									verticalArrangement = Arrangement.Center
								) {
									ListItem(
										overlineContent = {
											if (it.reviewsQuantity != 0) {
												Row(
													verticalAlignment = Alignment.CenterVertically,
													horizontalArrangement = Arrangement.spacedBy(8.dp)
												) {
													val rate = it.rating.div(0.5).toInt()
													val color = when (rate) {
														in 0..3 -> {
															theme.colorScheme.error
														}
														
														in 4..7 -> {
															Color(0xFFF3AA60)
														}
														
														else -> {
															Color(0xFF22A699)
														}
													}
													
													CircularProgressIndicator(
														progress = it.rating.div(5).toFloat(),
														Modifier.size(10.dp),
														strokeWidth = 2.dp,
														color = color
													)
													Text(text = it.rating.toString())
												}
											} else {
												Text(text = "Нет отзывов")
											}
										},
										headlineContent = {
											Text(text = it.name)
											
										},
										supportingContent = { Text(text = it.address) },
									)
									
								}
							}
							AnimatedVisibility(visible = selectedPlace != null && selectedPlace == it) {
								Column(Modifier.height(100.dp), verticalArrangement = Arrangement.SpaceAround) {
									IconButton(onClick = { /*TODO*/ }) {
										Icon(imageVector = Icons.Rounded.BookmarkBorder, contentDescription = null)
									}
									IconButton(onClick = { /*TODO*/ }) {
										Icon(imageVector = Icons.Rounded.RemoveRedEye, contentDescription = null)
									}
								}
							}
						}
						
					}
				}
			}
		}
	}
}
