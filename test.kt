import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import android.content.Context

fun test(context: Context) {
    DefaultRenderersFactory(context).setMediaCodecSelector(MediaCodecSelector.DEFAULT)
}
