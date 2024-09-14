import com.google.mlkit.vision.barcode.Barcode
import kotlin.reflect.KFunction0

class tcCache {

    companion object {
        private lateinit var instance: tcCache
        var ItemCache : HashMap<Barcode, String> = HashMap<Barcode, String> ()

        @JvmStatic fun get(): tcCache {
            return instance;
        }

        @JvmStatic fun getMap(): HashMap<Barcode, String> {
            return ItemCache;
        }
    }

    init {
        if (instance == null) {
            instance = this
        }
    }

}