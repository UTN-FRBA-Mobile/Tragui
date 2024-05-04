import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.LocalGlanceId
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.vasco.tragui.R

class DrinkWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = DrinkWidget
}

object DrinkWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val size = LocalSize.current
        val context = LocalContext.current
        val glanceId = LocalGlanceId.current
        GlanceTheme {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .appWidgetBackground()
                    .background(GlanceTheme.colors.background)
                    .cornerRadius(16.dp),
            ) {
//                when (imageState) {
//                    ImageState.Loading -> LoadingState()
//                    is ImageState.Success -> SuccessState(path = imageState.url)
//                }
                SuccessState()
            }
        }
    }

    @Composable
    private fun SuccessState() {
        Image(
            painter = painterResource(id = R.drawable.esto_no_es_coca_papi),
            contentDescription = "Wine"
        )
        Text(
            text = "Tap to refresh",
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = 12.sp,
            ),
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(GlanceTheme.colors.surface)
        )
    }

//    @Composable
//    private fun LoadingState() {
//        CircularProgressIndicator()
//    }
//
//    override suspend fun onDelete(context: Context, glanceId: GlanceId) {
//        super.onDelete(context, glanceId)
//        ImageWorker.cancel(context, glanceId)
//    }
//
//    private fun getImageProvider(path: String): ImageProvider {
//        if (path.startsWith("content://")) {
//            return ImageProvider(path.toUri())
//        }
//        val bitmap = BitmapFactory.decodeFile(path)
//        return ImageProvider(bitmap)
//    }
}