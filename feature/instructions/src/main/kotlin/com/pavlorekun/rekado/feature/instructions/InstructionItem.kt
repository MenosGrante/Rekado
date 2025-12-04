package com.pavlorekun.rekado.feature.instructions

import androidx.annotation.StringRes
import com.pavelrekun.rekado.core.ui.R
import kotlinx.collections.immutable.persistentListOf

data class InstructionItem(
    @param:StringRes val titleId: Int,
    @param:StringRes val descriptionId: Int,
    val isRcmInstruction: Boolean = false
)

val instructions = persistentListOf(
    InstructionItem(
        titleId = R.string.instructions_category_cable,
        descriptionId = R.string.instructions_category_cable_description
    ),
    InstructionItem(
        titleId = R.string.instructions_category_payload,
        descriptionId = R.string.instructions_category_payload_description
    ),
    InstructionItem(
        titleId = R.string.instructions_category_rcm,
        descriptionId = R.string.instructions_category_rcm_description,
        isRcmInstruction = true
    ),
    InstructionItem(
        titleId = R.string.instructions_category_final,
        descriptionId = R.string.instructions_category_final_description
    )
)