package com.chiassons.ChiassonInvMgr;

public enum BarcodeType {
    // Common barcode types
    UPC_A("UPC-A"),
    UPC_E("UPC-E"),
    EAN_8("EAN-8"),
    EAN_13("EAN-13"),
    ISBN_10("ISBN-10"),
    ISBN_13("ISBN-13"),
    CODE_39("CODE 39"),
    CODE_128("CODE 128"),
    ITF("ITF"),
    QR_CODE("QR Code"),
    DATA_MATRIX("Data Matrix"),
    PDF_417("PDF 417"),
    AZTEC("Aztec"),
    MaxiCode("MaxiCode"),
    RSS_14("RSS-14"),
    RSS_EXPANDED("RSS Expanded"),
    CODABAR("Codabar"),
    MSI("MSI"),
    POSTNET("PostNet"),
    PLANET("Planet"),
    Unknown("Unknown");

    private final String displayName;

    BarcodeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}