package ir.sajjad.bleconnector.domain

import java.io.IOException

class TransferFailedException: IOException("Reading incoming data failed")