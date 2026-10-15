package com.odwa.moodtracker.presentation.onboarding

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.odwa.moodtracker.R
import com.odwa.moodtracker.presentation.components.BrandRow
import com.odwa.moodtracker.presentation.viewmodel.OnBoardingViewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(viewModel: OnBoardingViewModel = hiltViewModel()) {
    OnboardingContent(onFinish = { viewModel.onEvent(OnBoardingEvent.Complete) })
}

@Composable
fun OnboardingContent(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    initialPage: Int = 0
) {
    val colors = MaterialTheme.colorScheme
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    val currentPage = pagerState.currentPage
    val isFirst = currentPage == 0
    val isLast = currentPage == onboardingPages.lastIndex

    fun goTo(page: Int) {
        scope.launch { pagerState.animateScrollToPage(page) }
    }

    BackHandler(enabled = !isFirst) { goTo(currentPage - 1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BrandRow()
            if (!isLast) {
                TextButton(onClick = onFinish) {
                    Text(stringResource(R.string.onboarding_skip))
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp)
        ) { index ->
            OnboardingPageContent(onboardingPages[index])
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(count = onboardingPages.size, current = currentPage)

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isFirst) {
                    TextButton(onClick = { goTo(currentPage - 1) }) {
                        Text(stringResource(R.string.onboarding_back))
                    }
                }
                Button(
                    onClick = { if (isLast) onFinish() else goTo(currentPage + 1) },
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        stringResource(
                            if (isLast) R.string.onboarding_get_started
                            else R.string.onboarding_next
                        )
                    )
                }
            }
        }
    }
}