package kovp.trainhard.domain_storage

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

val GymCardQualifier: Qualifier = object : Qualifier {
    override val value: QualifierValue = gymCardQualifier

    private val gymCardQualifier get() = "GYM_CARD_QUALIFIER"
}
