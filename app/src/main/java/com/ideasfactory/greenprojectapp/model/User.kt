package com.ideasfactory.greenprojectapp.model

class User(
    var userFirstName : String ="",
    var userLastName : String = "",
    var userBirth : String ="",
    var userEmail : String = "",
    var userAddress : String = "",
    var userPostalCode : String = "",
    var userCity : String = "",
    var userCountry : String = ""
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userFirstName != other.userFirstName) return false
        if (userLastName != other.userLastName) return false
        if (userBirth != other.userBirth) return false
        if (userEmail != other.userEmail) return false
        if (userAddress != other.userAddress) return false
        if (userPostalCode != other.userPostalCode) return false
        if (userCity != other.userCity) return false
        if (userCountry != other.userCountry) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userFirstName.hashCode()
        result = 31 * result + userLastName.hashCode()
        result = 31 * result + userBirth.hashCode()
        result = 31 * result + userEmail.hashCode()
        result = 31 * result + userAddress.hashCode()
        result = 31 * result + userPostalCode.hashCode()
        result = 31 * result + userCity.hashCode()
        result = 31 * result + userCountry.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(userFirstName='$userFirstName', userLastName='$userLastName', userBirth='$userBirth', userEmail='$userEmail', userAddress='$userAddress', userPostalCode='$userPostalCode', userCity='$userCity', userCountry='$userCountry')"
    }
}
