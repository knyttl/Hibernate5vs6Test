package net.goout

import jakarta.persistence.Access
import jakarta.persistence.AccessType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapKeyColumn
import jakarta.persistence.MapKeyEnumerated
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.io.Serializable
import java.util.EnumMap

@Entity
@Table(name = "car")
class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    var id: Long? = null
        set(value) {
            field = value
            for (country in Country.values()) {
                brandNames[country]?.carId = value
            }
        }

    var name: String? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @MapKeyColumn(name = "country")
    @MapKeyEnumerated(EnumType.ORDINAL)
    var brandNames: MutableMap<Country, CarName> = EnumMap(Country::class.java)

    init {
        for (country in Country.values()) {
            brandNames.put(country, CarName(null, country))
        }
    }
}

@Entity
@Table(name = "carname")
class CarName(
    @Id @Column(name = "car_id")
    var carId: Long? = null,
    @Id
    var country: Country? = null
) : Serializable

enum class Country {
    FRANCE,
    ENGLAND,
    CZECHIA,
}
