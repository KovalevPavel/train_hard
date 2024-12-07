package kovp.trainhard.home_presentation.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kovp.trainhard.core_domain.DATE_FORMAT_dd_MMMM
import kovp.trainhard.core_domain.formatToDateString
import kovp.trainhard.home_presentation.R
import kovp.trainhard.home_presentation.components.CurrentDateCard
import kovp.trainhard.home_presentation.components.ExercisePlanCardComposable
import kovp.trainhard.home_presentation.home.presentation.TodayPlan
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.NoProgramSelected
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.RestDay
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.TrainingDay
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun HomeScreen(
    todayPlan: TodayPlan,
    dateString: Long,
    modifier: Modifier = Modifier,
    onDateClick: () -> Unit,
) {
    val locale = LocalContext.current.resources.configuration.locales[0]

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
    ) {
        item {
            CurrentDateCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                currentDate = dateString.formatToDateString(DATE_FORMAT_dd_MMMM, locale),
                currentProgramName = "",
                onDateClick = onDateClick,
            )
        }
        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.today_plan),
                style = themeTypography.header1,
            )
        }
        mapTodayPlan(plan = todayPlan)
    }
}

private fun LazyListScope.mapTodayPlan(plan: TodayPlan) {
    when (plan) {
        NoProgramSelected -> {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    text = stringResource(id = R.string.today_plan_no_selected),
                    style = themeTypography.body2,
                    textAlign = TextAlign.Center,
                )
            }
        }

        RestDay -> {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    text = stringResource(id = R.string.today_plan_rest_day),
                    style = themeTypography.body2,
                    textAlign = TextAlign.Center,
                )
            }
        }

        is TrainingDay -> {
            items(plan.items, key = { it.id }) {
                ExercisePlanCardComposable(exercise = it)
            }
        }
    }
}
