package com.evening.counter.model

data class AccountingUiModel(
    val date: String,
    val orderNumber: String,
    val diameter: String,
    val thickness: String,
    val length: String,
    val piecesPerBundle: String,
    val bundleWeight: String,
    val totalBundles: String,
    val totalPieces: String,
    val totalWeight: String,
    val unitPrice: String,
    val totalAmount: String
)