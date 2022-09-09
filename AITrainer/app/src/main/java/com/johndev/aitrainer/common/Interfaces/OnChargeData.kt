package com.johndev.aitrainer.Interfaces

import com.johndev.aitrainer.common.entities.ChargeData

interface OnChargeData {
    fun onClick(chargeData: ChargeData)
}