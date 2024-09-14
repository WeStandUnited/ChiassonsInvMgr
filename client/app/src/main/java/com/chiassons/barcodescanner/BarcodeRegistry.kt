import com.google.mlkit.vision.barcode.Barcode

object BarcodeRegistry {
    private val barcodeMap: HashMap<Barcode, String> = HashMap()

    // Method to add a new entry to the map
    fun addBarcode(barcode: Barcode, description: String) {
        barcodeMap[barcode] = description
    }

    // Method to get the description for a given barcode
    fun getDescription(barcode: Barcode): String? {
        return barcodeMap[barcode]
    }

    // Optional: Method to get all barcodes and their descriptions
    fun getAllEntries(): Map<Barcode, String> {
        return barcodeMap.toMap()
    }
}