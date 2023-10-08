package me.kovp.trainhard.domain_storage

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

val GymCardQualifier: Qualifier = object : Qualifier {
    override val value: QualifierValue = GYM_CARD_QUALIFIER
}
