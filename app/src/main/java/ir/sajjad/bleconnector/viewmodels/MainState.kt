package ir.sajjad.bleconnector.viewmodels

import ir.sajjad.bleconnector.domain.Data


data class MainState(
    val chartEntryModel: Array<Float> = emptyArray<Float>(),
    val chartEntryModel2: Array<Float> = emptyArray<Float>(),
    val secondChartEntryModel:   Array<Float> = emptyArray<Float>(),
    val secondChartEntryModel2:   Array<Float> = emptyArray<Float>(),
    val result:MutableList<Data> = mutableListOf<Data>(),
    var productError:Boolean= false,
    var reviewError:Boolean= false,
)